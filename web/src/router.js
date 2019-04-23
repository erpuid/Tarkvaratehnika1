import Vue from 'vue'
import Router from 'vue-router'
import store from './store'
import Home from './views/Home.vue';
import About from './views/About.vue';
import Login from './views/Login.vue';
import WorkoutPlans from './views/WorkoutPlans.vue';
import CreateWorkout from './views/CreateWorkout.vue';
import Calendar from './views/Calendar.vue';
import auth from "./store/modules/auth";

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      component: About
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/plans',
        name: 'workoutplans',
        component: WorkoutPlans
    },
    {
        path: '/createworkout',
        name: 'createworkout',
        component: CreateWorkout
    },
    {
        path: '/calendar',
        name:'calendar',
        component: Calendar
    }
  ]
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/home', '/about', '/'];
    const authRequired = !publicPages.includes(to.path);
    // console.log("authRequire: " + authRequired);
    const loggedIn = store.getters.isAuthenticated;
    // console.log("loggedIn: " + loggedIn);
    // console.log(localStorage.getItem('token'));
    if (authRequired && localStorage.getItem('token') === null) {
        return next('/login');
    }
    next();
});

export default router;
