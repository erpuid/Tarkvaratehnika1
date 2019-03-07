<template>
    <div class="saveTraining">
        <h3>Add new exercise to workout</h3>
        <form id="add-workout" class="workoutClass" @submit.prevent="addExercise">
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
                    <td><label>Weight</label></td>
                    <td><input type="number" name="exerciseWeight" v-model="exercise.weight"/></td>
                </tr>
                <tr>
                    <td><input type="submit" class="submit" name="addExercise" value="Add"/></td>
                </tr>
            </table>
            <span class="exerciseData">
                <ol>
                    <li v-for="exercise in exercises">
                        <span>Name: {{exercise.exerciseName}},</span>
                        <span>sets: {{exercise.sets}},</span>
                        <span>repetitions: {{exercise.repetitions}},</span>
                        <span>weight: {{exercise.weight}}</span>
                        <button v-on:click="removeExercise(exercises.indexOf(exercise))" class="remove">Remove</button>
                        <br>
                    </li>
                </ol>
            </span>
        </form>
        <form id="training-form" method="post" @submit.prevent="processForm">
            <h3>Add new workout</h3>
            <table>
                <tr>
                    <td><label>Workout type</label></td>
                    <td><input type="text" name="workoutType" v-model="workoutType"/></td>
                </tr>
                <tr>
                    <td><label>Date</label></td>
                    <td><input type="date" name="date" v-model="date"/></td>
                </tr>
                <tr>
                    <td><input type="submit" class="submit" name="Submit" value="Submit workout"/></td>
                </tr>
            </table>
        </form>
        <form>
            <select v-model="selected">
                <option v-for="workoutPlan in workoutPlans" v-bind:value="workoutPlan">{{workoutPlan.workoutType}}</option>
            </select>
            <p>{{this.selected}}</p>
        </form>
    </div>
</template>

<script>
    import axios from "axios";
    export default {
        name: "SaveTraining",
        data: function() {
            return {
                workoutType: '',
                date: '',
                exercise: {
                    exerciseName: '',
                    sets: '',
                    repetitions: '',
                    weight: ''
                },
                exercises: [],
                workoutPlans: [],
                selected: ''
            }
        },
        datatype: 'json',
        methods: {
            processForm: function() {
                axios
                    .post('http://localhost:8080/api/workouts', {
                    workoutType: this.workoutType,
                    date: this.date,
                    exercises: this.exercises
                })
                    .then(response => console.log(response))
                this.exercises = [];
                this.workoutType = this.date = '';
            },
            addExercise: function() {
                this.exercises.push(JSON.parse(JSON.stringify(this.exercise)));
                this.exercise.exerciseName = this.exercise.sets = this.exercise.repetitions = this.exercise.weight = '';

            },
            removeExercise: function(exercise) {
                this.exercises.splice(exercise, 1);
            },
            getAllData: function() {
                axios
                    .get('http://localhost:8080/api/workouts')
                    .then(response => {
                        this.workoutPlans = response.data;
                    })
            }
        },
        created: function() {
            this.getAllData()
        }
    }
</script>

<style scoped>
    .saveTraining {
        font-family: 'Oswald', sans-serif;
    }

    .exerciseData {
        display: inline-block;
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

    * {
        margin: 5px;
    }

    ul {
        text-align: center;
    }
</style>