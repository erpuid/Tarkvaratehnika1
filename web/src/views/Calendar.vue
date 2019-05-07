<template>
    <div id="calendar">
        <div class="calendarView">
            <calendar-view
                    :events="events"
                    :show-date="showDate"
                    :time-format-options="{hour: 'numeric', minute:'2-digit', year:'numeric', month:'numeric', day:'numeric'}"
                    :enable-drag-drop="false"
                    :disable-past="disablePast"
                    :disable-future="disableFuture"
                    :show-event-times="showEventTimes"
                    :display-period-uom="displayPeriodUom"
                    :display-period-count="displayPeriodCount"
                    :starting-day-of-week="startingDayOfWeek"
                    :current-period-label="useTodayIcons ? 'icons' : ''"
                    @click-date="onClickDay"
                    @click-event="onClickEvent"
                    class="theme-default holiday-us-traditional holiday-us-official">
                <calendar-view-header
                        slot="header"
                        slot-scope="t"
                        :header-props="t.headerProps"
                        @input="setShowDate"/>
            </calendar-view>
        </div>

        <div class="calendarBox">
            <calendar-box :calendar-date="calendarDate"
                          :saving-visible="savingVisible"
                          :event-visible="eventVisible"
                          :selected-workout="selectedWorkout"
                          :show-exercises="showExercises"
                          @refresh-calendar="refreshCalendar">

            </calendar-box>
        </div>

        <!--<div class="selectedWorkout" v-if="this.selectedWorkout.id && eventVisible">
            <span>Workout Name: {{this.selectedWorkout.workoutName}} </span>
            <br>
            <span>Date: {{this.selectedWorkout.date}}</span>
            <br>
            <div v-for="exercise in this.selectedWorkout.exercises" class="exercise" @click="displayExerciseInfo(exercise.exerciseName)">
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

        <div class="selectedDay" v-if="this.calendarDate && savingVisible">
            <SaveTraining :date="calendarDate" @workout-saved="refreshCalendar"/>
        </div>-->

    </div>
</template>


<script>
    import {
        CalendarView,
        CalendarViewHeader,
        CalendarMathMixin
    } from "vue-simple-calendar"
    import axios from "axios";
    import SaveTraining from "../components/SaveTraining.vue";
    import CalendarBox from "../components/CalendarBox.vue";
    require("vue-simple-calendar/static/css/default.css");
    require("vue-simple-calendar/static/css/holidays-us.css");

    export default {
        name: 'app',
        components: {
            CalendarView,
            CalendarViewHeader,
            SaveTraining,
            CalendarBox
        },
        mixins: [CalendarMathMixin],
        data: function() {
            return {
                savingVisible: false,
                eventVisible: false,
                calendarDate: "",
                showDate: this.thisMonth(1),
                message: "",
                startingDayOfWeek: 1,
                disablePast: false,
                disableFuture: false,
                displayPeriodUom: "month",
                displayPeriodCount: 1,
                showEventTimes: true,
                newEventTitle: "",
                newEventStartDate: "",
                newEventEndDate: "",
                useDefaultTheme: true,
                useHolidayTheme: true,
                useTodayIcons: false,
                events: [],
                selectedWorkout: {
                    id: 0,
                    date: '0-0-0',
                    workoutName: '',
                    exercises: []
                },
                history: [],
                showExercises: []
            }
        },
        mounted() {
            this.newEventStartDate = this.newEventEndDate = this.isoYearMonthDay(this.today());
            this.getAllData();
        },
        methods: {
            thisMonth(d, h, m) {
                const t = new Date();
                return new Date(t.getFullYear(), t.getMonth(), d, h || 0, m || 0);
            },
            setShowDate(d) {
                this.message = `Changing calendar view to ${d.toLocaleDateString()}`;
                this.showDate = d;
            },
            onClickDay(d) {
                this.eventVisible = false;
                this.savingVisible = true;
                this.calendarDate = `${new Date (d.getTime() - (d.getTimezoneOffset() * 60000 ))
                    .toISOString().split("T")[0] }`;
                this.message = `You clicked: ${d.toLocaleDateString()}`;
            },
            onClickEvent(e) {
                this.showExercises = [];
                this.savingVisible = false;
                this.eventVisible = true;
                for (var i = 0; i < this.history.length; i++) {
                    if (this.history[i].id === e.id) {
                        this.selectedWorkout = this.history[i];
                    }
                }
                this.message = `You clicked: ${e.title}`;
            },
            getAllData: function() {
                this.username = localStorage.username;
                axios
                    .get('http://localhost:8080/api/workouts/' + this.username + '?access_token=' + localStorage.getItem('token'))
                    .then(response => {
                        this.history = response.data;
                        for (var i = 0; i < this.history.length; i++) {
                            this.events.push({
                                startDate: this.history[i].date,
                                endDate: this.history[i].date,
                                title: this.history[i].workoutName,
                                id: this.history[i].id
                            })
                        }
                    });
            },
            refreshCalendar: function() {
                this.calendarDate = "";
                this.events = [];
                this.history = [];
                this.getAllData();
            }
        }
    }
</script>


<style scoped>
    #calendar {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        height: 67vh;
        width: 95vw;
        margin-left: 20px;
        margin-right: 0;
        margin-top: 20px;
    }
    .calendarView {
        height: 55vh;
        width: 60vw;
        display: inline-block;
        float: left;
        margin-right: 2ex;
    }
    .calendarBox {
        float: right;
        margin-right: 20%;
        width: 200px;
    }
</style>