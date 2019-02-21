<template>
  <div id="app">
    <Header />
    <router-view/>
  </div>
</template>

<script>
    import Header from './components/layout/Header';
    import Home from './views/Home.vue';
    import About from './views/About.vue';
    import Login from './views/Login.vue';
    import SaveTraining from './views/SaveTraining.vue';
    import WorkoutHistory from './views/WorkoutHistory.vue';

    export default {
        name: 'app',
        components: {
            Header,
            Home,
            About,
            Login,
            SaveTraining,
            WorkoutHistory
        }
    }
</script>







<style>
  #app {
    /*font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;*/
  }
</style>
