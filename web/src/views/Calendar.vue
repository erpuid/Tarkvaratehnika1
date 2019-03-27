<template>
    <div id="calendar">
        <div class="calendarView">
            <calendar-view
                    :events="events"
                    :show-date="showDate"
                    :time-format-options="{hour: 'numeric', minute:'2-digit'}"
                    :enable-drag-drop="false"
                    :disable-past="disablePast"
                    :disable-future="disableFuture"
                    :show-event-times="showEventTimes"
                    :display-period-uom="displayPeriodUom"
                    :display-period-count="displayPeriodCount"
                    :starting-day-of-week="startingDayOfWeek"
                    :current-period-label="useTodayIcons ? 'icons' : ''"
                    @drop-on-date="onDrop"
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

        <div class="selectedWorkout" v-if="this.selectedWorkout.id">
            <span>Workout Name: {{this.selectedWorkout.workoutName}} </span>
            <br>
            <span>Date: {{this.selectedWorkout.date}}</span>
            <br>
            <div v-for="exercise in this.selectedWorkout.exercises" style="margin-left: 2ex;" id="exercise">
                <span @click="displayExerciseInfo(exercise.exerciseName)">Exercise: {{exercise.exerciseName}} </span>
                <div v-if="showExercises.includes(exercise.exerciseName)">
                    <span>Sets: {{exercise.sets}} </span>
                    <br>
                    <span>Repetitions: {{exercise.repetitions}} </span>
                    <br>
                    <span v-if="exercise.weight !== 0">Weight: {{exercise.weight}}</span>
                </div>
            </div>
        </div>

    </div>
</template>


<script>
    import {
        CalendarView,
        CalendarViewHeader,
        CalendarMathMixin,
    } from "vue-simple-calendar"
    import axios from "axios";
    require("vue-simple-calendar/static/css/default.css");
    require("vue-simple-calendar/static/css/holidays-us.css");

    export default {
        name: 'app',
        components: {
            CalendarView,
            CalendarViewHeader,
        },
        mixins: [CalendarMathMixin],
        data: function() {
            return {
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
        computed: {
            userLocale() {
                return this.getDefaultBrowserLocale
            },
            dayNames() {
                return this.getFormattedWeekdayNames(this.userLocale, "long", 0)
            },
            themeClasses() {
                return {
                    "theme-default": this.useDefaultTheme,
                    "holiday-us-traditional": this.useHolidayTheme,
                    "holiday-us-official": this.useHolidayTheme,
                }
            },
        },
        mounted() {
            this.newEventStartDate = this.isoYearMonthDay(this.today())
            this.newEventEndDate = this.isoYearMonthDay(this.today())
            this.getAllData()
        },
        methods: {
            thisMonth(d, h, m) {
                const t = new Date()
                return new Date(t.getFullYear(), t.getMonth(), d, h || 0, m || 0)
            },
            onClickDay(d) {
                this.message = `You clicked: ${d.toLocaleDateString()}`
            },
            onClickEvent(e) {
                for (var i = 0; i < this.history.length; i++) {
                    if (this.history[i].id === e.id) {
                        this.selectedWorkout = this.history[i];
                    }
                }
                this.message = `You clicked: ${e.title}`
            },
            setShowDate(d) {
                this.message = `Changing calendar view to ${d.toLocaleDateString()}`
                this.showDate = d
            },
            onDrop(event, date) {
                this.message = `You dropped ${event.id} on ${date.toLocaleDateString()}`
                // Determine the delta between the old start date and the date chosen,
                // and apply that delta to both the start and end date to move the event.
                const eLength = this.dayDiff(event.startDate, date)
                event.originalEvent.startDate = this.addDays(event.startDate, eLength)
                event.originalEvent.endDate = this.addDays(event.endDate, eLength)
            },
            getAllData: function() {
                console.log(this.username);
                this.username = localStorage.username;
                console.log(this.username);
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


<style>
    #calendar {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        color: #2c3e50;
        height: 67vh;
        width: 90vw;
        margin-left: 20px;
        margin-right: 0;
        margin-top: 144px;
    }
    .calendarView {
        height: 67vh;
        width: 60vw;
        display: inline-block;
    }
    .selectedWorkout {
        background: #f0f0f0;
        height: 37vh;
        width: 20vw;
        margin-right: 140px;
        overflow-y: auto;
        display: inline-block;
        float: right;
        padding: 10px;
    }
</style>