<template>
    <div class="savetraining">
        <h3>Add new exercise to workout</h3>
        <form id="add-workout" @submit.prevent="addExercise">
            <label>Add exercise</label>
            <input type="text" name="exerciseName" v-model="exercise.exerciseName"/>
            <br>
            <label>Sets</label>
            <input type="number" name="exerciseSets" v-model="exercise.sets"/>
            <br>
            <label>Repetitions</label>
            <input type="number" name="exerciseRepetitions" v-model="exercise.repetitions"/>
            <br>
            <label>Weight</label>
            <input type="number" name="exerciseWeight" v-model="exercise.weight"/>
            <br>
            <input type="submit" class="submit" name="addExercise" value="Add"/>
        </form>
        <h3>Add new workout</h3>
        <form id="training-form" method="post" @submit.prevent="processForm">
            <label>Workout type</label>
            <input type="text" name="workoutType" v-model="workoutType"/>
            <br>
            <label>Date</label>
            <input type="date" name="date" v-model="date"/>
            <br>
            <input type="submit" class="submit" name="Submit" value="Submit workout"/>
        </form>
        <ul>
            <li v-for="exercise in exercises">
                {{exercise}}
                <button v-on:click="removeExercise(exercises.indexOf(exercise))">Remove</button>
            </li>
        </ul>
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
                exercises: []
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
            }
        }
    }
</script>

<style scoped>
    .savetraining {
        font-family: 'Oswald', sans-serif;
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

    * {
        margin: 5px;
    }

    ul {
        float: right;
    }
</style>