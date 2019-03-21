import {AUTH_LOGOUT, AUTH_REQUEST} from "../constants";
import axios from "axios";

const state = { token: localStorage.getItem('token') || '' };

const getters = {
    isAuthenticated: state => !!state.token
};

const actions = {
    [AUTH_REQUEST]: ({commit, dispatch}, user) => {
        return new Promise((resolve, reject) => {
            commit(AUTH_REQUEST, user);
            resolve()
        })
    },
    [AUTH_LOGOUT]: ({commit, dispatch}) => {
        return new Promise((resolve, reject) => {
            commit(AUTH_LOGOUT);
            localStorage.removeItem('user'); // clear your user's token from localstorage
            localStorage.removeItem('username');
            resolve()
        })
    }
};

const mutations = {
    [AUTH_REQUEST]: (state, user) => {
        let data = 'username='+user.username+'&password='+user.password;
        let headers = {
            'Content-type': 'application/x-www-form-urlencoded'
        };

        var params = new URLSearchParams();
        params.append('grant_type', 'password');
        params.append('username', user.username);
        params.append('password',user.password);
        axios({
            method: 'post',
            url: 'http://localhost:8080/oauth/token',
            auth: {username: 'my-trusted-client', password: 'secret'},
            headers: {"Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
            data: params
        }).then(response => {
            console.log(response.data.access_token);
            console.log(response.data);
            localStorage.setItem('token', response.data.access_token);
            localStorage.setItem('user', user);
            localStorage.setItem('username', user.username);
        });
        /*
        axios.post('http://localhost:8080/login', data, {
            headers: headers,
            /*auth:{
                username: this.username,
                password: this.password
            }
        }).then(response => {
            console.log(JSON.stringify(user));
            state.user = user;
            localStorage.setItem('user', user);
            localStorage.setItem('token', response.data.access_token);
            localStorage.username = user.username;
            console.log(user.username + "peale sättimist");

        }).catch(error => {
            state.user = '';
            localStorage.removeItem('user');
        });
        */
    },

    [AUTH_LOGOUT]: () => {
        axios.get('http://localhost:8080/logout/'+localStorage.getItem('token')).then(response => {
            localStorage.removeItem('user'); // clear your user's token from localstorage
            getters.isAuthenticated(false);
            state.user = '';
            localStorage.username = '';
        });
    }
};

export default {
    state,
    getters,
    actions,
    mutations,
}