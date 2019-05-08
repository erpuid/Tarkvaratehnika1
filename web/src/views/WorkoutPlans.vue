<template>
    <div class="workouthistory">
        <h1 class="pageName">Workout plans</h1>
        <div class="clearfix"></div>

        <div class="filterButtons">
            <button class="filterButton" @click="filterDifficulty('all')">Show all</button>
            <button class="filterButton" @click="filterDifficulty('beginner')">Beginner</button>
            <button class="filterButton" @click="filterDifficulty('intermediate')">Intermediate</button>
            <button class="filterButton" @click="filterDifficulty('advanced')">Advanced</button>
            <button class="filterButton" @click="filterDifficulty('favourites')">Favourites</button>
        </div>

        <ul>
            <li v-for="workoutPlan in workoutPlans" v-bind:key="workoutPlan">
                <h4 class="clickableText" @click="displayPlanInfo(workoutPlan.id)">{{workoutPlan.planName}} - {{workoutPlan.difficulty}}</h4>
                <div class="workoutContainer" v-if="showPlans.includes(workoutPlan.id)">
                    <div class="favouriteContainer">
                    <button class="favouriteButton" @click="saveToFavourite(workoutPlan.id)" v-if="!favouritesIds.includes(workoutPlan.id)">SAVE to favourites</button>
                    <button class="favouriteButton" @click="saveToFavourite(workoutPlan.id)" v-if="favouritesIds.includes(workoutPlan.id)">REMOVE from favourites</button>
                    </div>
                    <div class="workoutDescription">{{workoutPlan.description}}</div>
                    <div v-for="workout in workoutPlan.workouts" v-bind:key="workout">
                        <hr>
                        <span>Workout Name: {{workout.workoutName}} </span><br>
                        <div class="exerciseContainer" v-for="exercise in workout.planExercises" v-bind:key="exercise" style="margin-left: 2ex;" id="exercise">
                            <span>Exercise: {{exercise.exerciseName}}</span>
                            <span class="workout">Sets: {{exercise.sets}}</span>
                            <span class="workout">Repetitions: {{exercise.repetitions}}</span>
                            <div class="clickableText workoutVideo" @click="displayVideos(exercise.videoURL)">View instructional video</div>
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
                let i;
                //console.log("PLAN ID: " + planId);
                axios.post('http://localhost:8080/api/plan/favourite/' + planId + '?access_token=' + localStorage.getItem('token'));
                if (this.favouritesIds.includes(planId)) {
                    let index = this.favouritesIds.indexOf(planId);
                    if (index > -1) {
                        this.favouritesIds.splice(index, 1);
                        let plan;
                        for (i = 0; i < this.favourites.length; i++) {
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
                    for (i = 0; i < this.allPlans.length; i++) {
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
                //console.log(this.workoutPlans.pl)
            },
            filterDifficulty: function(value) {
                let i;
                this.workoutPlans = [];
                this.showPlans = [];
                if (value === "all") {
                    this.workoutPlans = this.allPlans;
                } else if (value === "beginner") {
                    for (i = 0; i < this.allPlans.length; i++) {
                        if (this.allPlans[i].difficulty === "BEGINNER") {
                            this.workoutPlans.push(this.allPlans[i]);
                        }
                    }
                } else if (value === "intermediate") {
                    for (i = 0; i < this.allPlans.length; i++) {
                        if (this.allPlans[i].difficulty === "INTERMEDIATE") {
                            this.workoutPlans.push(this.allPlans[i]);
                        }
                    }
                } else if (value === "advanced") {
                    for (i = 0; i < this.allPlans.length; i++) {
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