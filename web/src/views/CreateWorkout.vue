<template>
    <div class="createWorkout">
        <div v-if="planNameSelected">
            <h3>{{planName}}</h3>
            <p>Difficulty: {{difficulty}}</p>
            <p>Description: {{planDescription}}</p>
            <p v-if="workoutNameSelected">Workout Name: {{workoutName}}</p>
        </div>
        <form id="workout-plan" @submit.prevent="savePlanName" v-if="!planNameSelected">
            <table>
                <tr>
                    <td><label for="planName">Workout plan name</label></td>
                </tr>
                <tr>
                    <td><input id="planName" type="text" name="planName" v-model="planName" required="required"/></td>
                </tr>
                <tr>
                    <td><label for="difficulty">PLAN difficulty:</label></td>
                </tr>
                <tr>
                    <td>
                        <select id="difficulty" v-model="difficulty" required="required" >
                            <option>Beginner</option>
                            <option>Intermediate</option>
                            <option>Advanced</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><label for="description">Plan description</label></td>
                </tr>
                <tr>
                    <td><textarea id="description" rows="4" cols="40" v-model="planDescription" required="required"></textarea></td>
                </tr>
                <tr>
                    <td><input type="submit" class="submit" name="Submit" value="Submit"/></td>
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
                    <td><input type="text" name="workoutName" v-model="workoutName" required="required"/></td>
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
                    <td><input type="text" name="exerciseName" required="required" v-model="exercise.exerciseName"/></td>
                </tr>
                <tr>
                    <td><label>Sets</label></td>
                    <td><input type="number" name="exerciseSets" min="1" max="999" required="required" v-model="exercise.sets"/></td>
                </tr>
                <tr>
                    <td><label>Repetitions</label></td>
                    <td><input type="number" name="exerciseRepetitions" min="1" max="999" required="required" v-model="exercise.repetitions"/></td>
                </tr>
                <tr>
                    <td><label>Video url</label></td>
                    <td><input type="text" name="description"  v-model="exercise.videoURL"/></td>
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
            <input type="submit" class="submit" name="addWorkout" value="Add Workout"/>
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
            <input type="submit" class="submit" name="submitPlan" value="Submit plan"/>
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
                planDescription: '',
                workoutName: '',
                difficulty: '',
                exercise: {
                    exerciseName: '',
                    sets: '',
                    repetitions: '',
                    videoURL: ''
                },
                planWorkout: {
                    workoutName: '',
                    description: '',
                    planExercises: []
                },
                workouts: [],
                errors: []
            }
        },
        methods: {
            processForm: function() {
                axios
                    .post('http://localhost:8080/api/plan?access_token='+localStorage.getItem('token'), {
                        planName: this.planName,
                        description: this.planDescription,
                        difficulty: this.difficulty,
                        workouts: this.workouts
                    })
                    .then(response => console.log(response));
                this.planNameSelected = this.workoutNameSelected = false;
                this.planName = this.workoutName = '';
                this.workouts = [];
            },
            savePlanName: function() {
                if (this.planName !== '' && this.difficulty !== '' && this.planDescription !== '') {
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
                this.validateForm();
                if (this.errors.length === 0) {
                    this.planWorkout.planExercises.push(JSON.parse(JSON.stringify(this.exercise)));
                    this.exercise.exerciseName = this.exercise.sets = this.exercise.repetitions = this.exercise.videoURL = '';
                }
            },
            validateForm: function() {
                this.errors = [];
                if (isNaN(this.exercise.sets) || isNaN(this.exercise.repetitions)) {
                    this.errors.push("Only numbers allowed.")
                }
                if (this.exercise.sets < 1 || this.exercise.repetitions < 1) {
                    this.errors.push("Values must be positive.")
                }
                if (this.exercise.sets > 999 || this.exercise.repetitions > 999) {
                    this.errors.push("Values must be smaller than 999.")
                }
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
    .submit {
        font-weight: bold;
        font-family: 'Oswald', sans-serif;
        padding: 2px 5px;
        margin-top: 10px;
    }

    form {
        padding-top: 15px;
    }
</style>