<template>
    <div class="workouthistory">
        <h1>Workout history</h1>
        <div v-for="h in history" id="workout">
                <span>Id: {{h.id}} </span>
                <span>Workout type: {{h.workoutType}} </span>
                <span>Date: {{h.date}}</span>
            <div v-for="exercise in h.exercises" style="margin-left: 2ex;" id="exercise">
                <span>Name: {{exercise.exerciseName}} </span>
                <span>Sets: {{exercise.sets}} </span>
                <span>Repetitions: {{exercise.repetitions}} </span>
                <span>Weight: {{exercise.weight}}</span>
            </div>
            <br>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "WorkoutHistory",
        data: function() {
            return {
                history: []
            }
        },
        methods: {
            getAllData: function() {
                axios
                    .get('http://localhost:8080/api/workouts', {
                        headers: {
                            'Access-Control-Allow-Origin': '*'
                        }
                    })
                    .then(response => {
                        this.history = response.data;
                        console.log(this.history);
                    })
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
</style>