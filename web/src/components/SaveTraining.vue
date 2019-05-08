<template>
    <div class="saveTraining">
        <div v-if="this.exercises.length === 0">
            <label>Date: {{changeDate}}</label>
            <br>
            <select v-model="selectedPlan">
                <option v-for="workoutPlan in workoutPlans" v-bind:value="workoutPlan">{{workoutPlan.planName}}</option>
            </select>
            <br>
            <select v-model="selectedWorkout" @change="changeExercise" v-if="this.selectedPlan">
                <option v-for="workout in this.selectedPlan.workouts" v-bind:value="workout">{{workout.workoutName}}</option>
            </select>
        </div>

        <div v-if="this.exercises.length">
            <p>{{selectedPlan.planName}}</p>
            <p>{{selectedWorkout.workoutName}}</p>
        </div>

        <form id="add-workout" class="workoutClass" @submit.prevent v-if="this.selectedWorkout">
            <table>
                <div v-if="this.index < this.selectedWorkout.planExercises.length">
                    <tr>
                        <td><label>{{this.selectedWorkout.planExercises[this.index].exerciseName}}</label></td>
                    </tr>
                    <tr>
                        <td><label>Sets</label></td>
                        <td><input type="number" name="exerciseSets" min="1" max="999" v-model="exercise.sets"/></td>
                    </tr>
                    <tr>
                        <td><label>Repetitions</label></td>
                        <td><input type="number" name="exerciseRepetitions" min="1" max="999" v-model="exercise.repetitions"/></td>
                    </tr>
                    <tr>
                        <td><label>Weight</label></td>
                        <td><input type="number" name="exerciseWeight"  min="0" max="999"v-model="exercise.weight"/></td>
                    </tr>
                    <tr>
                        <td><input type="submit" class="submit" name="addExercise" value="Add" @click="addExercise"/></td>
                        <td><input type="submit" class="submit" name="skipExercise" value="Skip" @click="skipExercise"/></td>
                    </tr>
                </div>
                <tr v-if="this.exercises.length > 0">
                    <td><input type="submit" class="submit" name="submitAll" value="Submit all" @click="processForm"/></td>
                </tr>
            </table>
        </form>

        <span class="exerciseData">
            <ol>
                <li v-for="exercise in exercises">
                    <span>Name: {{exercise.exerciseName}} | </span>
                    <span>sets: {{exercise.sets}} | </span>
                    <span>repetitions: {{exercise.repetitions}}</span>
                    <span v-if="exercise.weight"> | weight: {{exercise.weight}}</span>
                    <button v-on:click="removeExercise(exercises.indexOf(exercise))" class="remove">Remove</button>
                    <br>
                </li>
            </ol>
        </span>
    </div>
</template>

<script>
    import axios from "axios";
    import {BASE_URL} from "../store/constants";
    export default {
        name: "SaveTraining",
        props: {
            date: { type: String, default: undefined}
        },
        data: function() {
            return {
                selectedDate: '',
                userName: '',
                workoutName: '',
                exercise: {
                    exerciseName: '',
                    sets: '',
                    repetitions: '',
                    weight: ''
                },
                planExercises: [],
                exercises: [],
                workoutPlans: [],
                selectedPlan: '',
                selectedWorkout: '',
                index: 0,
                errors: []
            }
        },
        datatype: 'json',
        methods: {
            processForm: function() {
                axios
                    .post(BASE_URL + 'api/workouts?access_token='+localStorage.getItem('token'), {
                    userName: localStorage.username,
                    workoutName: this.selectedWorkout.workoutName,
                    date: this.selectedDate,
                    exercises: this.exercises
                })
                    .then(response => this.$emit("workout-saved"));
                this.exercises = [];
                this.workoutName = this.selectedDate = '';
                this.index = 0;
            },
            validateForm: function() {
                this.errors = [];
                if (isNaN(this.exercise.sets) || isNaN(this.exercise.repetitions) || isNaN(this.exercise.weight)) {
                    this.errors.push("Only numbers allowed.")
                }
                if (this.exercise.sets < 1 || this.exercise.repetitions < 1) {
                    this.errors.push("Values must be positive.")
                }
                if (this.exercise.sets > 999 || this.exercise.repetitions > 999 || this.exercise.weight > 999) {
                    this.errors.push("Values must be smaller than 999.")
                }
                if (this.exercise.weight < 0) {
                    this.errors.push("Weight must be 0 or higher.")
                }
            },
            addExercise: function() {
                this.validateForm();
                if (this.errors.length === 0) {
                    this.exercises.push(JSON.parse(JSON.stringify(this.exercise)));
                    this.exercise.exerciseName = this.exercise.sets = this.exercise.repetitions = this.exercise.weight = '';
                    this.index++;
                    if (this.index < this.selectedWorkout.planExercises.length) {
                        this.updateExercise();
                    }
                }
            },
            skipExercise: function() {
                this.exercise.exerciseName = this.exercise.sets = this.exercise.repetitions = this.exercise.weight = '';
                this.index++;
                if (this.index < this.selectedWorkout.planExercises.length) {
                    this.updateExercise();
                } else if (this.index > this.selectedWorkout.planExercises.length - 1 && this.exercises.length === 0) {
                    this.index = 0;
                    this.updateExercise();
                }
            },
            removeExercise: function(exercise) {
                this.exercises.splice(exercise, 1);
                if (this.index > this.selectedWorkout.planExercises.length - 1 && this.exercises.length === 0) {
                    this.index = 0;
                    this.selectedWorkout = '';
                }
            },
            getWorkoutPlans: function() {
                axios
                    .get(BASE_URL + 'api/plan/favourite?access_token='+localStorage.getItem('token'))
                    .then(response => {
                        this.workoutPlans = response.data;
                    })
            },
            updateExercise: function() {
                this.exercise.exerciseName = this.selectedWorkout.planExercises[this.index].exerciseName;
                this.exercise.sets = this.selectedWorkout.planExercises[this.index].sets;
                this.exercise.repetitions = this.selectedWorkout.planExercises[this.index].repetitions;
                this.exercise.weight = '';
            },
            changeExercise: function () {
                this.index = 0;
                this.updateExercise();
            }
        },
        computed: {
          changeDate() {
              this.selectedDate = this.date;
              return this.selectedDate;
          }
        },
        created: function() {
            this.getWorkoutPlans()
        }
    }
</script>

<style scoped>
    .saveTraining {
        font-family: 'Oswald', sans-serif;
    }

    .exerciseData {
        display: inline-block;
        margin: 5px;
    }

    .workoutClass {
        display: flex;
        flex-direction: row;
    }

    .submit {
        font-weight: bold;
        font-family: 'Oswald', sans-serif;
        font-size: 16px;
        padding: 5px;
        background-color: #ccffcc;
        border-color: #66ff66;
    }

    .submit:hover {
        background-color: #66ff66;
        border-color: #66ff66;
    }

    .remove {
        background-color: #ff8080;
        border-color: #ff0000;
        font-weight: bold;
    }

    .remove:hover {
        background-color: #ff0000;
        border-color: #ff0000;
        color: white;
    }

    ul {
        text-align: center;
    }

    select {
        width: 150px;
        margin: 2px;
        border: none;
    }
    p {
        margin: 0;
    }
</style>