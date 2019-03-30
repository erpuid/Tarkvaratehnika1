import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import store from './store'
import login from './views/Login'
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
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import(/* webpackChunkName: "about" */ './views/About.vue')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('./views/Login.vue')
    },
    {
        path: '/history',
        name: 'workouthistory',
        component: () => import('./views/WorkoutHistory.vue')
    },
    {
        path: '/createworkout',
        name: 'createworkout',
        component: () => import('./views/CreateWorkout.vue')
    },
    {
        path: '/calendar',
        name:'calendar',
        component: () => import('./views/Calendar.vue')
    }
  ]
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login'];
    const authRequired = !publicPages.includes(to.path);
    console.log("authRequire: " + authRequired);
    const loggedIn = store.getters.isAuthenticated;
    console.log("loggedIn: " + loggedIn);
    console.log(localStorage.getItem('token'));
    if (authRequired && localStorage.getItem('token') === null) {
        return next('/login');
    }
    next();
});

export default router;
