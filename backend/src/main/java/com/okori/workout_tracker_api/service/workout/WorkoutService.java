package com.okori.workout_tracker_api.service.workout;

import com.okori.workout_tracker_api.dto.ExerciseDTO;
import com.okori.workout_tracker_api.dto.WorkoutDTO;
import com.okori.workout_tracker_api.dto.WorkoutScheduleDTO;
import com.okori.workout_tracker_api.entity.Exercise;
import com.okori.workout_tracker_api.entity.Workout;
import com.okori.workout_tracker_api.entity.WorkoutSchedule;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.repository.ExerciseRepository;
import com.okori.workout_tracker_api.repository.WorkoutRepository;
import com.okori.workout_tracker_api.repository.WorkoutScheduleRepository;
import com.okori.workout_tracker_api.request.AddWorkoutRequest;
import com.okori.workout_tracker_api.request.WorkoutUpdateRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: Work on business logic validations
@Service
@Transactional
public class WorkoutService implements IWorkoutService {
    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private ExerciseRepository exerciseRepository;
    @Autowired
    private WorkoutScheduleRepository workoutScheduleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Workout addWorkout(AddWorkoutRequest request) {
        return workoutRepository.save(createWorkout(request));
    }

    // Can be implemented with a mapper
    private Workout createWorkout(AddWorkoutRequest request) {
        Workout newWorkout = new Workout(
                request.getName(),
                request.getCategory(),
                request.getDescription());

        for (ExerciseDTO exerciseDto : request.getExercises()) {
            Exercise newExercise = new Exercise(
                    exerciseDto.getName(),
                    exerciseDto.getType(),
                    exerciseDto.getDescription(),
                    newWorkout);
            newExercise.setWorkout(newWorkout);
            newWorkout.getExercises().add(newExercise);
        }

        for (WorkoutScheduleDTO workoutScheduleDto : request.getWorkoutSchedules()) {
            WorkoutSchedule newWorkoutSchedule = new WorkoutSchedule(
                    // TODO: Validate Date and Time input
                    //  it should be following:
                    //  Date: yyyy-MM-dd
                    //  Time: HH:mm:ss
                    workoutScheduleDto.getDate(),
                    workoutScheduleDto.getTime(),
                    newWorkout);
            newWorkout.getWorkoutSchedules().add(newWorkoutSchedule);
        }

        return newWorkout;
    }

    @Override
    public Workout getWorkoutById(Long id) throws ResourceNotFoundException {
        return workoutRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found."));
    }

    @Override
    public Workout updateWorkout(WorkoutUpdateRequest request, Long workoutId) {
        return workoutRepository
                .findById(workoutId)
                .map(existingWorkout -> updateExistingWorkout(existingWorkout, request))
                .map(workoutRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found. Update aborted."));
    }

    private Workout updateExistingWorkout(Workout existingWorkout, WorkoutUpdateRequest request) {
        existingWorkout.setName(request.getName());
        existingWorkout.setCategory(request.getCategory());
        existingWorkout.setDescription(request.getDescription());

        return existingWorkout;
    }

    @Override
    public void deleteWorkoutById(Long id) throws ResourceNotFoundException {
        // Verify if the workout id exists in the database
        workoutRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found. Deletion aborted."));

        // Delete the workout if found
        workoutRepository.deleteById(id);
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public List<Workout> getWorkoutsByName(String name) {
        return workoutRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public List<Workout> getWorkoutsByCategory(String category) {
        return workoutRepository.findAllByCategory(category);
    }

    @Override
    public List<Workout> getWorkoutsByNameAndCategory(String name, String category) {
        return workoutRepository.findAllByNameContainingIgnoreCaseAndCategory(name, category);
    }

    @Override
    public int countWorkoutsByCategory(String category) {
        return workoutRepository.findAllByCategory(category).size();
    }

    @Override
    public List<WorkoutDTO> getConvertedWorkouts(List<Workout> workouts) {
        return workouts.stream().map(this::convertToDto).toList();
    }

    @Override
    public WorkoutDTO convertToDto(Workout workout) {
        WorkoutDTO workoutDTO = modelMapper.map(workout, WorkoutDTO.class);

        List<Exercise> exercises = exerciseRepository.findByWorkoutId(workout.getId());
        List<ExerciseDTO> exerciseDtos = exercises
                .stream()
                .map(exercise -> modelMapper.map(exercise, ExerciseDTO.class))
                .toList();
        workoutDTO.setExercises(exerciseDtos);

        List<WorkoutSchedule> workoutSchedules = workoutScheduleRepository.findByWorkoutId(workout.getId());
        List<WorkoutScheduleDTO> workoutScheduleDtos = workoutSchedules
                .stream()
                .map(workoutSchedule -> modelMapper.map(workoutSchedule, WorkoutScheduleDTO.class))
                .toList();
        workoutDTO.setWorkoutSchedules(workoutScheduleDtos);

        return workoutDTO;
    }
}
