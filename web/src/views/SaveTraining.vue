<template>
    <div class="saveTraining">
        <div v-if="this.exercises.length === 0">
        <select v-model="selectedPlan">
            <option v-for="workoutPlan in workoutPlans" v-bind:value="workoutPlan">{{workoutPlan.planName}}</option>
        </select>
        <br>
        <select v-model="selectedWorkout" @change="updateExercise" v-if="this.selectedPlan">
            <option v-for="workout in this.selectedPlan.workouts" v-bind:value="workout">{{workout.workoutName}}</option>
        </select>
        </div>

        <form id="add-workout" class="workoutClass" @submit.prevent v-if="this.selectedWorkout">
            <table>
                <tr>
                    <td><label>Date</label></td>
                </tr>
                <tr>
                    <td><p>{{changeDate}}</p></td>
                </tr>
                <div v-if="this.index < this.selectedWorkout.planExercises.length">
                <tr>
                    <td><label>{{this.selectedWorkout.planExercises[this.index].exerciseName}}</label></td>
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
                    <td><input type="submit" class="submit" name="addExercise" value="Add" @click="addExercise"/></td>
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
                    <span>Name: {{exercise.exerciseName}},</span>
                    <span>sets: {{exercise.sets}},</span>
                    <span>repetitions: {{exercise.repetitions}},</span>
                    <span>weight: {{exercise.weight}}</span>
                    <button v-on:click="removeExercise(exercises.indexOf(exercise))" class="remove">Remove</button>
                    <br>
                </li>
            </ol>
        </span>
    </div>
</template>

<script>
    import axios from "axios";
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
                index: 0
            }
        },
        datatype: 'json',
        methods: {
            processForm: function() {
                axios
                    .post('/api/workouts?access_token='+localStorage.getItem('token'), {
                    userName: localStorage.username,
                    workoutName: this.selectedWorkout.workoutName,
                    date: this.selectedDate,
                    exercises: this.exercises
                })
                    .then(response => console.log(response));
                this.exercises = [];
                this.workoutName = this.selectedDate = '';
                this.index = 0;
                this.$emit("workout-saved");
            },
            addExercise: function() {
                this.exercises.push(JSON.parse(JSON.stringify(this.exercise)));
                this.exercise.exerciseName = this.exercise.sets = this.exercise.repetitions = this.exercise.weight = '';
                this.index++;
                if (this.index < this.selectedWorkout.planExercises.length) {
                    this.updateExercise();
                }
            },
            removeExercise: function(exercise) {
                this.exercises.splice(exercise, 1);
            },
            getWorkoutPlans: function() {
                axios
                    .get('/api/plan?access_token='+localStorage.getItem('token'))
                    .then(response => {
                        this.workoutPlans = response.data;
                    })
            },
            updateExercise: function() {
                if (this.index > this.exercises.length) {
                    this.index = 0;
                }
                this.exercise.exerciseName = this.selectedWorkout.planExercises[this.index].exerciseName;
                this.exercise.sets = this.selectedWorkout.planExercises[this.index].sets;
                this.exercise.repetitions = this.selectedWorkout.planExercises[this.index].repetitions;
                this.exercise.weight = '';
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
        /*margin: 5px;*/
    }

    ul {
        text-align: center;
    }
</style>