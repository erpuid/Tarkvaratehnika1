<template>
    <div class="workouthistory">
        <h1>Workout history</h1>
        <ul>
            <li v-for="h in newHistory" id="workout">
                    <span>Workout Name: {{h.workoutName}}, </span>
                    <span>Date: {{h.date}}</span>
                <div v-for="exercise in h.exercises" style="margin-left: 2ex;" id="exercise">
                    <span>Name: {{exercise.exerciseName}}, </span>
                    <span>Sets: {{exercise.sets}}, </span>
                    <span>Repetitions: {{exercise.repetitions}}, </span>
                    <span>Weight: {{exercise.weight}}</span>
                </div>
                <br>
            </li>
        </ul>

        <form id="workout-search" @submit.prevent="workoutSearch">
            <label>Search by:</label>
            <br>
            <input type="radio" name="searchType" value="type" v-model="searchType"/>Workout Name
            <br>
            <input type="radio" name="searchType" value="date" v-model="searchType"/>Date (YYYY-MM-DD)
            <br>
            <input type="radio" name="searchType" value="exerciseName" v-model="searchType"/>Exercise Name
            <br>
            <input v-if="this.searchType === 'type'" type="text" name="searchField" v-model="searchField"/>
            <input v-else-if="this.searchType === 'date'" type="date" name="searchField" v-model="searchField"/>
            <input v-else-if="this.searchType === 'exerciseName'" type="text" name="searchField" v-model="searchField"/>
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
                history: [],
                searchType: '',
                searchField: '',
                newHistory: [],
                username: ''
            }
        },
        methods: {
            getAllData: function() {
                console.log(this.username);
                this.username = localStorage.username;
                console.log(this.username);
                axios
                    .get('http://localhost:8080/api/workouts/'+this.username+'?access_token='+localStorage.getItem('token'))
                    .then(response => {
                        this.history = response.data;
                        this.newHistory = this.history;
                    })
            },
            workoutSearch: function() {
                this.newHistory = [];
                if (this.searchField === "") {
                    this.newHistory = this.history;
                } else if (this.searchType === "type") {
                    for (var i = 0; i < this.history.length; i++) {
                        if (this.history[i].workoutName === this.searchField) {
                            this.newHistory.push(this.history[i]);
                        }
                    }
                } else if (this.searchType === "date") {
                    for (var i = 0; i < this.history.length; i++) {
                        if (this.history[i].date === this.searchField) {
                            this.newHistory.push(this.history[i]);
                        }
                    }
                } else if (this.searchType === "exerciseName") {
                    for (var i = 0; i < this.history.length; i++) {
                        for (var j = 0; j< this.history[i].exercises.length; j++) {
                            if (this.history[i].exercises[j].exerciseName === this.searchField) {
                                this.newHistory.push(this.history[i]);
                                break;
                            }
                        }

                    }
                }
            }
        },
        created: function() {
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