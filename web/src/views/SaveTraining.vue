<template>
    <div class="savetraining">
        <form id="training-form" method="post" @submit.prevent="processForm">
            <label>Workout name</label>
            <input type="text" name="Workout-name" v-model="name"/>
            <br>
            <label>Workout type</label>
            <input type="text" name="Workout-type" v-model="type"/>
            <br>
            <input type="submit" name="Submit"/>
        </form>
    </div>
    
</template>

<script>
    // import {AxiosInstance as axios} from "axios";
    import axios from "axios";
    export default {
        name: "SaveTraining",
        data: {
            name: '',
            type: '',
        },
        methods: {
            processForm: function() {
                console.log({ name: this.name, type: this.type });
                alert('Processing!');
                axios
                    .get('http://localhost:8080/api/workouts', {
                        headers: {
                            'Access-Control-Allow-Origin': '*'
                        }
                    })
                    .then(response => console.log(response.data))
            }
        }
    }
</script>

<style scoped>

</style>