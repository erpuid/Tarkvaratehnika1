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
                        <li v-if="!loggedIn"><router-link to="/login">Login</router-link></li>
                        <li v-if="loggedIn"><router-link to="/plans">Workout Plans</router-link></li>
                        <li v-if="loggedIn"><router-link to="/createworkout">Create Workout</router-link></li>
                        <li v-if="loggedIn"><router-link to="/calendar">Calendar</router-link></li>
                        <li v-if="loggedIn"><button class="logout" @click="logout">Log out</button></li>
                    </ul>
                </nav>
            </div>
        </header>
    </div>
</template>

<script>
    import {AUTH_LOGOUT} from "../../store/constants";
    import login from "../../views/Login.vue"

    export default {
        name: 'logout',
        data: function () {
            return {
                loggedIn: false,
            }
        },
        components: {
            login
        },
        methods: {
            logout: function () {
                this.$store.dispatch(AUTH_LOGOUT)
                    .then(() => {
                        this.$router.push('/login')
                    });
                this.loggedIn = false;
            }
        },
        created() {
            this.loggedIn = this.$store.getters.isAuthenticated;
            this.$bus.$on('logged', () => {
                this.loggedIn = true;
            });
        }
    }
</script>

