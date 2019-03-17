<template>
    <header class="header">
        <h1>YourFitness – better life starts here</h1>
        <div id="nav">
            <router-link to="/">Home</router-link> |
            <router-link to="/about">About</router-link> |
            <router-link to="/login">Login</router-link> |
            <router-link to="/savetraining">Save Training</router-link> |
            <router-link to="/history">Workout History</router-link> |
            <router-link to="/createworkout">Create Workout</router-link>
        </div>
    </header>
</template>

<script>
    export default {
        name: 'Header'
    }
</script>

<style scoped>

    .header {
        font-family: 'Righteous', cursive;
        background: #99ebff;
        color: #000000;
        text-align: center;
        padding: 10px;
        font-weight: normal;
    }

    .header a {
        color: #262626;
        padding: 5px;
        text-decoration: none;
        font-weight: bold;
    }

    .header a:hover {
        color: #fff;
        background: black;

    }
</style>