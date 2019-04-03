<template>
    <div class="createWorkout">
        <form id="workout-plan" @submit.prevent="savePlanName" v-if="!planNameSelected">
            <table>
                <tr>
                    <td><label>Workout plan name</label></td>
                </tr>
                <tr>
                    <td><input type="text" name="planName" v-model="planName"/></td>
                </tr>
                <tr>
                    <td><input type="submit" name="Submit" value="Submit"/></td>
                </tr>
            </table>
            <br>
        </form>
        <form id="training-form" method="post" @submit.prevent="saveWorkoutName" v-if="planNameSelected && !workoutNameSelected">
            <table>
                <tr>
                    <td><label>Workout name</label></td>
                </tr>
                <tr>
                    <td><input type="text" name="workoutName" v-model="workoutName"/></td>
                </tr>
                <tr>
                    <td><input type="submit" class="submit" name="Submit" value="Submit workout"/></td>
                </tr>
            </table>
            <br>
        </form>
        <form id="add-workout" class="workoutClass" @submit.prevent="addExercise" v-if="workoutNameSelected === true">
            <table>
                <tr>
                    <td><label>Add exercise</label></td>
                    <td><input type="text" name="exerciseName" v-model="exercise.exerciseName"/></td>
                </tr>
                <tr>
                    <td><label>Sets</label></td>
                    <td><input type="number" name="exerciseSets" v-model="exercise.sets"/></td>
                </tr>
                <tr>
                    <td><label>Repetitions</label></td>
                    <td><input type="number" name="exerciseRepetitions" v-model="exercise.repetitions"/></td>
                </tr>
                <tr>
                    <td><input type="submit" class="submit" name="addExercise" value="Add"/></td>
                </tr>
            </table>
            <br>
        </form>

        <span class="exerciseData">
            <ol>
                <li v-for="exercise in planWorkout.planExercises">
                    <span>Name: {{exercise.exerciseName}} </span>
                    <span>sets: {{exercise.sets}} </span>
                    <span>repetitions: {{exercise.repetitions}} </span>
                    <button v-on:click="removeExercise(planWorkout.planExercises.indexOf(exercise))" class="remove">Remove</button>
                    <br>
                </li>
            </ol>
        </span>

        <form @submit.prevent="addWorkout" v-if="planWorkout.planExercises.length">
            <input type="submit" name="addWorkout" value="Add Workout"/>
        </form>

        <span v-if="workouts.length">
            <h4>Added workouts</h4>
            <ol>
                <li v-for="workout in workouts">
                    <span>Workout name: {{workout.workoutName}}</span>
                </li>
            </ol>
        </span>
        <form @submit.prevent="processForm" v-if="workouts.length">
            <input type="submit" class="class" name="submitPlan" value="Submit plan"/>
        </form>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        name: "CreateWorkout",
        data: function() {
            return {
                planNameSelected: false,
                workoutNameSelected: false,
                planName: '',
                workoutName: '',
                exercise: {
                    exerciseName: '',
                    sets: '',
                    repetitions: ''
                },
                planWorkout: {
                    workoutName: '',
                    planExercises: []
                },
                workouts: []
            }
        },
        methods: {
            processForm: function() {
                axios
                    .post('http://localhost:8080/api/plan?access_token='+localStorage.getItem('token'), {
                        planName: this.planName,
                        workouts: this.workouts
                    })
                    .then(response => console.log(response));
                this.planNameSelected = this.workoutNameSelected = false;
                this.planName = this.workoutName = '';
                this.workouts = [];
            },
            savePlanName: function() {
                if (this.planName !== '') {
                    this.planNameSelected = true;
                }
            },
            saveWorkoutName: function() {
                if (this.workoutName !== '') {
                    this.workoutNameSelected = true;
                }
            },
            removeExercise: function(exercise) {
                this.planWorkout.planExercises.splice(exercise, 1);
            },
            addExercise: function() {
                this.planWorkout.planExercises.push(JSON.parse(JSON.stringify(this.exercise)));
                this.exercise.exerciseName = this.exercise.sets = this.exercise.repetitions = '';

            },
            addWorkout: function() {
                this.planWorkout.workoutName = this.workoutName;
                this.workouts.push(this.planWorkout);
                this.planWorkout = { workoutName: '', planExercises: [] };
                this.workoutName = '';
                this.workoutNameSelected = false;
            }
        }
    }
</script>

<style scoped>

</style>