<template>
    <div class="workouthistory">
        <h1>Workout plans</h1>
        <ul>
            <li v-for="workoutPlan in workoutPlans">
                <h4>{{workoutPlan.planName}}</h4>
                <div v-for="workout in workoutPlan.workouts">
                    <span>Workout Name: {{workout.workoutName}} </span>

                    <div v-for="exercise in workout.planExercises" style="margin-left: 2ex;" id="exercise">
                        <span>Exercise: {{exercise.exerciseName}} </span>
                        <span>Sets: {{exercise.sets}} </span>
                        <span>Repetitions: {{exercise.repetitions}} </span>
                    </div>
                    <br>

                </div>
            </li>
        </ul>

        <form id="workout-search" @submit.prevent="workoutSearch">
            <label>Search by:</label>
            <br>
            <input type="radio" name="searchType" value="planName" v-model="searchType"/>Workout Plan Name
            <br>
            <input type="radio" name="searchType" value="workoutName" v-model="searchType"/>Workout Name
            <br>
            <input type="radio" name="searchType" value="exerciseName" v-model="searchType"/>Exercise Name
            <br>
            <input v-if="this.searchType === 'planName'" type="text" name="searchField" v-model="searchField"/>
            <input v-else-if="this.searchType === 'workoutName'" type="text" name="searchField" v-model="searchField"/>
            <input v-else-if="this.searchType === 'exerciseName'" type="text" name="searchField" v-model="searchField"/>
            <br>
            <input type="submit" class="submit" name="search" value="Search"/>
        </form>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "WorkoutHistory",
        data: function() {
            return {
                searchType: '',
                searchField: '',
                username: '',
                workoutPlans: [],
                allPlans: []
            }
        },
        methods: {
            getAllData: function() {
                axios
                    .get('http://localhost:8080/api/plan?access_token='+localStorage.getItem('token'))
                    .then(response => {
                        this.workoutPlans = this.allPlans = response.data;
                    })
            },
            workoutSearch: function() {
                this.workoutPlans = [];
                if (this.searchField === "") {
                    this.workoutPlans = this.allPlans;
                } else if (this.searchType === "planName") {
                    for (var i = 0; i < this.allPlans.length; i++) {
                        if (this.allPlans[i].planName === this.searchField) {
                            this.workoutPlans.push(this.allPlans[i]);
                        }
                    }
                } else if (this.searchType === "workoutName") {
                    for (var i = 0; i < this.allPlans.length; i++) {
                        for (var j = 0; j < this.allPlans[i].workouts.length; j++) {
                            if (this.allPlans[i].workouts[j].workoutName === this.searchField) {
                                this.workoutPlans.push(this.allPlans[i]);
                                break;
                            }
                        }
                    }
                } else if (this.searchType === "exerciseName") {
                    for (var i = 0; i < this.allPlans.length; i++) {
                        for (var j = 0; j < this.allPlans[i].workouts.length; j++) {
                            for (var k = 0; k < this.allPlans[i].workouts[j].planExercises.length; k++) {
                                if (this.allPlans[i].workouts[j].planExercises[k].exerciseName === this.searchField) {
                                    this.workoutPlans.push(this.allPlans[i]);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        },
        mounted() {
            this.getAllData()
        }
    }
</script>

<style scoped>
    .workouthistory {
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
</style>