<template>
    <div class="workouthistory">
        {{history}}
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "WorkoutHistory",
        data: function() {
            return {
                history: ''
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
                    })
            }
        },
        created: function() {
            this.getAllData()
        }
    }
</script>

<style scoped>

</style>