<template>
    <div class="super_container">

        <!-- Header -->
        <header class="header">
            <div class="header_content d-flex flex-row align-items-center justify-content-center">

                <!-- Logo -->
                <div class="logo top_align">
                    <a href="#"></a>
                    <div>Your<span>Fitness</span></div>
                    <div>better life starts here</div>
                </div>
                <nav class="main_nav">
                    <ul class="d-flex flex-row align-items-center justify-content-start">
                        <li><router-link to="/">Home</router-link></li>
                        <li><router-link to="/about">About</router-link></li>
                        <li><router-link to="/login">Login</router-link></li>
                        <li><router-link to="/history">Workout History</router-link></li>
                        <li><router-link to="/createworkout">Create Workout</router-link></li>
                        <li><router-link to="/calendar">Calendar</router-link></li>
                        <li><button class="logout" v-on:click="logout">Log out</button></li>
                    </ul>
                </nav>
            </div>
        </header>

        <!-- Menu -->

        <div class="menu">
            <div class="menu">
                <nav class="menu_nav">
                    <ul class="main" style="font-weight: bold">
                        <li><router-link to="/">Home</router-link></li>
                        <li><router-link to="/about">About</router-link></li>
                        <li><router-link to="/login">Login</router-link></li>
                        <li><router-link to="/savetraining">Save Training</router-link></li>
                        <li><router-link to="/history">Workout History</router-link></li>
                        <li><router-link to="/createworkout">Create Workout</router-link></li>
                    </ul>
                </nav>
            </div>
        </div>
    </div>
</template>

<script>
    import {AUTH_LOGOUT} from "../../store/constants";


    export default {
        name: 'logout',
        methods: {
            logout: function () {
                this.$store.dispatch(AUTH_LOGOUT)
                    .then(() => {
                        this.$router.push('/login')
                    })
            }
        },
    }
</script>



