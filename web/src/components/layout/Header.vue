<template>
    <div class="super_container">

        <!-- Header -->
        <header class="header">
            <div class="header_content d-flex flex-row align-items-center justify-content-center">

                <!-- Logo -->
                <div class="logo top_align">
                    <router-link to="/"></router-link>
                    <div>Your<span>Fitness</span></div>
                    <div>better life starts here</div>
                </div>
                <nav class="main_nav">
                    <ul class="d-flex flex-row align-items-center justify-content-start">
                        <li><router-link to="/">Home</router-link></li>
                        <li><router-link to="/about">About</router-link></li>
                        <li><router-link to="/plans">Workout Plans</router-link></li>
                        <li><router-link to="/createworkout">Create Workout</router-link></li>
                        <li><router-link to="/calendar">Calendar</router-link></li>
                        <li><button class="logout" @click="logout">Log out</button></li>
                    </ul>
                </nav>
            </div>
        </header>
    </div>
</template>

<script>
    import {AUTH_LOGOUT} from "../../store/constants";

    export default {
        name: 'logout',
        data: function () {
            return {
                loggedIn: this.isAuth
            }
        },
        methods: {
            logout: function () {
                this.$store.dispatch(AUTH_LOGOUT)
                    .then(() => {
                        this.$router.push('/login')
                    });
                this.loggedIn = false;
            },
            tokenInfo: function () {
                this.token = this.loggedIn;
                this.loggedIn = this.isAuth();
            },
            isAuth: function() {
                return this.$store.getters.isAuthenticated;
            }
        },
        mounted() {
            this.tokenInfo();
        }
    }
</script>

