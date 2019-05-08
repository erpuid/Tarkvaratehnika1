<template>
    <div class="workouthistory">
        <h1>Workout plans</h1>

        <div class="filterButtons">
            <button class="filterButton" @click="filterDifficulty('all')">Show all</button>
            <button class="filterButton" @click="filterDifficulty('beginner')">Beginner</button>
            <button class="filterButton" @click="filterDifficulty('intermediate')">Intermediate</button>
            <button class="filterButton" @click="filterDifficulty('advanced')">Advanced</button>
            <button class="filterButton" @click="filterDifficulty('favourites')">Favourites</button>
        </div>

        <ul>
            <li v-for="workoutPlan in workoutPlans">
                <h4 class="clickableText" @click="displayPlanInfo(workoutPlan.id)">{{workoutPlan.planName}} - {{workoutPlan.difficulty}}</h4>
                <div v-if="showPlans.includes(workoutPlan.id)">
                    <span>{{workoutPlan.description}}</span>
                    <br>
                    <button class="favouriteButton" @click="saveToFavourite(workoutPlan.id)" v-if="!favouritesIds.includes(workoutPlan.id)">SAVE to favourites</button>
                    <button class="favouriteButton" @click="saveToFavourite(workoutPlan.id)" v-if="favouritesIds.includes(workoutPlan.id)">REMOVE from favourites</button>
                    <div v-for="workout in workoutPlan.workouts">
                        <span>Workout Name: {{workout.workoutName}} </span><br>
                        <div v-for="exercise in workout.planExercises" style="margin-left: 2ex;" id="exercise">
                            <span>Exercise: {{exercise.exerciseName}} </span>
                            <span>Sets: {{exercise.sets}} </span>
                            <span>Repetitions: {{exercise.repetitions}} </span>
                            <div class="clickableText" @click="displayVideos(exercise.videoURL)">View instructional video</div>
                            <iframe width="420" height="315" :src="exercise.videoURL" v-if="visibleVideos.includes(exercise.videoURL)">
                            </iframe>
                        </div>
                        <br>
                    </div>
                </div>
            </li>
        </ul>
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
                allPlans: [],
                showPlans: [],
                favourites: [],
                favouritesIds: [],
                visibleVideos: []
            }
        },
        methods: {
            saveToFavourite : function(planId) {
                console.log("PLAN ID: " + planId);
                axios.post('http://localhost:8080/api/plan/favourite/' + planId + '?access_token=' + localStorage.getItem('token'));
                if (this.favouritesIds.includes(planId)) {
                    let index = this.favouritesIds.indexOf(planId);
                    if (index > -1) {
                        this.favouritesIds.splice(index, 1);
                        let plan;
                        for (var i = 0; i < this.favourites.length; i++) {
                            if (this.favourites[i].id === planId) {
                                plan = this.favourites[i];
                            }
                        }
                        let planIndex = this.favourites.indexOf(plan);
                        if (planIndex > -1) {
                            this.favourites.splice(index, 1)
                        }

                    }
                } else {
                    this.favouritesIds.push(planId);
                    for (var i = 0; i < this.allPlans.length; i++) {
                        if (this.allPlans[i].id === planId) {
                            this.favourites.push(this.allPlans[i]);
                        }
                    }
                }
            },
            getAllData: function() {
                axios
                    .get('http://localhost:8080/api/plan?access_token='+localStorage.getItem('token'))
                    .then(response => {
                        this.workoutPlans = this.allPlans = response.data;
                    });
                axios
                    .get('http://localhost:8080/api/plan/favourite?access_token='+localStorage.getItem('token'))
                    .then(response => {
                        this.favourites = response.data;
                        for (var i = 0; i < this.favourites.length; i++) {
                            this.favouritesIds.push(this.favourites[i].id);
                        }
                    });
                console.log(this.workoutPlans.pl)
            },
            filterDifficulty: function(value) {
                this.workoutPlans = [];
                this.showPlans = [];
                if (value === "all") {
                    this.workoutPlans = this.allPlans;
                } else if (value === "beginner") {
                    for (var i = 0; i < this.allPlans.length; i++) {
                        if (this.allPlans[i].difficulty === "BEGINNER") {
                            this.workoutPlans.push(this.allPlans[i]);
                        }
                    }
                } else if (value === "intermediate") {
                    for (var i = 0; i < this.allPlans.length; i++) {
                        if (this.allPlans[i].difficulty === "INTERMEDIATE") {
                            this.workoutPlans.push(this.allPlans[i]);
                        }
                    }
                } else if (value === "advanced") {
                    for (var i = 0; i < this.allPlans.length; i++) {
                        if (this.allPlans[i].difficulty === "ADVANCED") {
                            this.workoutPlans.push(this.allPlans[i]);
                        }
                    }
                } else if (value === "favourites") {
                    this.workoutPlans = this.favourites;
                }
            },
            displayPlanInfo: function(arg) {
                if(this.showPlans.includes(arg)) {
                    var index = this.showPlans.indexOf(arg);
                    if (index > -1) {
                        this.showPlans.splice(index, 1);
                    }
                } else {
                    this.showPlans.push(arg);
                }
            },
            displayVideos: function(arg) {
                if(this.visibleVideos.includes(arg)) {
                    var index = this.visibleVideos.indexOf(arg);
                    if (index > -1) {
                        this.visibleVideos.splice(index, 1);
                    }
                } else {
                    this.visibleVideos.push(arg);
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
        margin-left: auto;
        margin-right: auto;
        max-width: 70ex;
        background-color: #fff4e6;
        height: 100%;
        text-align: center;
        padding: 0 10px 0 10px;

    }
    .clickableText {
        background: #f4e6d4;
        margin: 4px 10px 4px 10px;
        padding: 2px 0 2px 0;
        border-radius: 5px;
    }
    .clickableText:hover {
        background: #ff9908;
        cursor: pointer;
    }
    .filterButtons {
        margin-bottom: 20px;
    }
    .filterButton {
        cursor: pointer;
        padding: 1px 4px 1px 4px;
        margin-right: 10px;
    }
    .favouriteButton {
        cursor: pointer;
        padding: 2px;
    }
</style>