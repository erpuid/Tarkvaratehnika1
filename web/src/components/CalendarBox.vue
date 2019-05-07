<template>
    <div class="calendarBox">
        <div class="selectedWorkout" v-if="selectedWorkout.id && eventVisible">
            <span>Date: {{selectedWorkout.date}}</span>
            <br>
            <span>Workout Name: {{selectedWorkout.workoutName}} </span>
            <br>
            <div v-for="exercise in selectedWorkout.exercises" class="exercise" @click="displayExerciseInfo(exercise.exerciseName)">
                <span>Exercise: {{exercise.exerciseName}} </span>
                <div v-if="showExercises.includes(exercise.exerciseName)">
                    <span>Sets: {{exercise.sets}} </span>
                    <br>
                    <span>Repetitions: {{exercise.repetitions}} </span>
                    <br>
                    <span v-if="exercise.weight !== 0">Weight: {{exercise.weight}}</span>
                </div>
            </div>
        </div>

        <div class="selectedDay" v-if="calendarDate && savingVisible">
            <SaveTraining :date="calendarDate" @workout-saved="$emit('refresh-calendar')"/>
        </div>
    </div>
</template>

<script>
    import SaveTraining from "../components/SaveTraining.vue";
    export default {
        name: "CalendarBox",
        components: {
            SaveTraining
        },
        props: {
            calendarDate: { type: String, default: undefined },
            savingVisible: { type: Boolean, default: false },
            eventVisible: { type: Boolean, default: false },
            selectedWorkout: {
                id: 0,
                date: '0-0-0',
                workoutName: '',
                exercises: []
            },
            showExercises: { type: Array, default: undefined }
        },
        methods: {
            displayExerciseInfo: function(arg) {
                if(this.showExercises.includes(arg)) {
                    var index = this.showExercises.indexOf(arg);
                    if (index > -1) {
                        this.showExercises.splice(index, 1);
                    }
                } else {
                    this.showExercises.push(arg);
                }
            }
        }
    }
</script>

<style scoped>
    .calendarBox {
        font-family: 'Oswald', sans-serif;
    }
    .selectedWorkout {
        background:  #f0f0f0;
        height: 55vh;
        width: 30vw;
        overflow: auto;
        padding: 10px;
        border-radius: 5px;
        cursor: default;
    }
    .selectedDay {
        background: #f0f0f0;
        height: 55vh;
        width: 30vw;
        overflow: auto;
        padding: 10px;
        border-radius: 5px;
        cursor: default;
    }
    .exercise {
        background: #f4e6d4;
        margin: 2px 2px 2px 2ex;
        padding: 2px 2px 2px 6px;
        border-radius: 5px;
    }
    .exercise:hover {
        background: #ff9908;
        cursor: pointer;
    }
</style>