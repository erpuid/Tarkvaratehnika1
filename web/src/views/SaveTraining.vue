<template>
    <div class="savetraining">
        <form id="training-form" method="post" @submit.prevent="processForm">
            <label>Workout type</label>
            <input type="text" name="workoutType" v-model="workoutType"/>
            <br>
            <label>Date</label>
            <input type="text" name="date" v-model="date"/>
            <br>
            <input type="submit" name="Submit"/>
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
                date: ''
            }
        },
        datatype: 'json',
        methods: {
            processForm: function() {
                console.log(this.workoutType);
                console.log(JSON.stringify({workoutType: this.workoutType, date: this.date}));
                alert('Processing!');
                axios
                    .post('http://localhost:8080/api/workouts', {
                    workoutType: this.workoutType,
                    date: this.date
                })
                    .then(response => console.log(response))
                /*axios
                    .get('http://localhost:8080/api/workouts', {
                        headers: {
                            'Access-Control-Allow-Origin': '*'
                        }
                    })
                    .then(response => console.log(response.data))*/
            }
        }
    }
</script>

<style scoped>

</style>