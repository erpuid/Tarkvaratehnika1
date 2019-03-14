<template>
    <div class="createWorkout">
        <form id="workout-plan" @submit.prevent="savePlanName" v-if="displayPlan === false">
            <table>
                <tr>
                    <td><label>Workout plan name</label></td>
                    <td><input type="text" name="planName" v-model="planName"/></td>
                </tr>
                <tr>
                    <td><input type="submit" name="Submit" value="Submit"/></td>
                </tr>
            </table>
        </form>
        <br>
        <form id="training-form" method="post" @submit.prevent="saveWorkoutName" v-if="displayPlan === true && displayWorkout === false">
            <table>
                <tr>
                    <td><label>Workout name</label></td>
                    <td><input type="text" name="workoutName" v-model="workoutName"/></td>
                </tr>
                <tr>
                    <td><input type="submit" class="submit" name="Submit" value="Submit workout"/></td>
                </tr>
            </table>
        </form>
        <br>
        <form id="add-workout" class="workoutClass" @submit.prevent="addExercise" v-if="displayWorkout === true">
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
        </form>
        <form @submit.prevent="processForm" v-if="displayWorkout === true">
                <input type="submit" class="class" name="submitPlan" value="Submit plan"/>
        </form>
        <span class="exerciseData">
            <ol>
                <li v-for="exercise in planExercises">
                    <span>Name: {{exercise.exerciseName}},</span>
                    <span>sets: {{exercise.sets}},</span>
                    <span>repetitions: {{exercise.repetitions}},</span>
                    <button v-on:click="removeExercise(planExercises.indexOf(exercise))" class="remove">Remove</button>
                    <br>
                </li>
            </ol>
        </span>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        name: "CreateWorkout",
        data: function() {
            return {
                displayPlan: false,
                displayWorkout: false,
                planName: '',
                workoutName: '',
                exercise: {
                    exerciseName: '',
                    sets: '',
                    repetitions: ''
                },
                planExercises: [],
                workouts: []
            }
        },
        methods: {
            processForm: function() {
                axios
                    .post('http://localhost:8080/api/plan', {
                        planName: this.planName,
                        workouts: this.workouts
                    })
                    .then(response => console.log(response))
                this.planExercises = [];
                this.workoutName = this.date = '';
            },
            savePlanName: function() {
                if (this.planName !== '') {
                    this.displayPlan = true;
                }
            },
            saveWorkoutName: function() {
                if (this.workoutName !== '') {
                    this.displayWorkout = true;
                }
            },
            removeExercise: function(exercise) {
                this.planExercises.splice(exercise, 1);
            },
            addExercise: function() {
                this.planExercises.push(JSON.parse(JSON.stringify(this.exercise)));
                this.exercise.exerciseName = this.exercise.sets = this.exercise.repetitions = '';
                this.workouts.push({workoutName: this.workoutName, planExercises: this.planExercises});
            }
        }
    }
</script>

<style scoped>

</style>