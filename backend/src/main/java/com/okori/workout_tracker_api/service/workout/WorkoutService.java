package com.okori.workout_tracker_api.service.workout;

import com.okori.workout_tracker_api.dto.ExerciseDTO;
import com.okori.workout_tracker_api.dto.WorkoutDTO;
import com.okori.workout_tracker_api.dto.WorkoutScheduleDTO;
import com.okori.workout_tracker_api.entity.Exercise;
import com.okori.workout_tracker_api.entity.User;
import com.okori.workout_tracker_api.entity.Workout;
import com.okori.workout_tracker_api.entity.WorkoutSchedule;
import com.okori.workout_tracker_api.exceptions.ResourceNotFoundException;
import com.okori.workout_tracker_api.exceptions.UnauthorizedAccessException;
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
    public Workout addWorkout(AddWorkoutRequest request, User user) {
        Workout newWorkout = createWorkout(request);
        newWorkout.setUser(user);
        return workoutRepository.save(newWorkout);
    }

    private Workout createWorkout(AddWorkoutRequest request) {
        Workout newWorkout = new Workout();
        newWorkout.setName(request.getName());
        newWorkout.setCategory(request.getCategory());
        newWorkout.setDescription(request.getDescription());

        for (ExerciseDTO exerciseDto : request.getExercises()) {
            Exercise newExercise = new Exercise(
                    exerciseDto.getName(),
                    exerciseDto.getType(),
                    exerciseDto.getDescription(),
                    newWorkout);
            newWorkout.getExercises().add(newExercise);
        }

        for (WorkoutScheduleDTO workoutScheduleDto : request.getWorkoutSchedules()) {
            WorkoutSchedule newWorkoutSchedule = new WorkoutSchedule(
                    // TODO: Validate Date and Time input
                    // it should be following:
                    // Date: yyyy-MM-dd
                    // Time: HH:mm:ss
                    workoutScheduleDto.getDate(),
                    workoutScheduleDto.getTime(),
                    newWorkout);
            newWorkout.getWorkoutSchedules().add(newWorkoutSchedule);
        }

        return newWorkout;
    }

    @Override
    public Workout getWorkoutById(Long id, User user) throws ResourceNotFoundException, UnauthorizedAccessException {
        Workout existingWorkout = workoutRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found."));

        if (!existingWorkout.getUser().equals(user)) {
            throw new UnauthorizedAccessException("You are not authorized to access this user");
        }

        return existingWorkout;
    }

    @Override
    public List<Workout> getAllWorkoutsForUser(User user) {
        return workoutRepository.findAllByUser(user);
    }

    @Override
    public Workout updateWorkout(WorkoutUpdateRequest request, Long workoutId, User user) throws ResourceNotFoundException, UnauthorizedAccessException {
        // Ensure the authenticated user has access to the requested workout data
        Workout existingWorkout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found. Update aborted"));

        if (!existingWorkout.getUser().equals(user)) {
            throw new UnauthorizedAccessException("You are not authorized to access this user");
        }

        Workout updatedWorkout = updateExistingWorkout(existingWorkout, request);
        return workoutRepository.save(updatedWorkout);
    }

    private Workout updateExistingWorkout(Workout existingWorkout, WorkoutUpdateRequest request) {
        existingWorkout.setName(request.getName());
        existingWorkout.setCategory(request.getCategory());
        existingWorkout.setDescription(request.getDescription());

        return existingWorkout;
    }

    @Override
    public void deleteWorkoutById(Long id, User user) throws ResourceNotFoundException {
        Workout existingWorkout = workoutRepository
                .findByUserAndId(user, id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout not found. Deletion aborted."));

        if (!existingWorkout.getUser().equals(user)) {
            throw new UnauthorizedAccessException("You are not authorized to access this user");
        }

        workoutRepository.deleteById(id);
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

    @Override
    public List<WorkoutDTO> getConvertedWorkouts(List<Workout> workouts) {
        return workouts.stream().map(this::convertToDto).toList();
    }
}