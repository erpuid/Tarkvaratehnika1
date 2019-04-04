<template>
  <div id="app">
    <Header />
    <div class="home">
      <router-view/>
    </div>
    <Footer/>
  </div>
</template>

<script>
    import Header from './components/layout/Header';
    import Footer from './components/layout/Footer';
    import Home from './views/Home.vue';
    import About from './views/About.vue';
    import Login from './views/Login.vue';
    import WorkoutPlans from './views/WorkoutPlans.vue';
    import CreateWorkout from './views/CreateWorkout.vue';
    import Calendar from './views/Calendar.vue';

    export default {
        name: 'app',
        components: {
            Header,
            Footer,
            Home,
            About,
            Login,
            WorkoutPlans,
            CreateWorkout,
            Calendar
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
    height: 100%;
  }

  /*Notice: Possibly removed after general design is ready*/
  .home {
    margin-top: 124px;
  }

</style>
