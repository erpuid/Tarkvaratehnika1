import {AUTH_REQUEST} from "../constants";
import axios from "axios";

const state = { user: localStorage.getItem('user') || '' };

const getters = {
    isAuthenticated: state => !!state.user
};

const actions = {
    [AUTH_REQUEST]: ({commit, dispatch}, user) => {
        return new Promise((resolve, reject) => {
            commit(AUTH_REQUEST, user);
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

        axios.post('http://localhost:8080/login', data, {
            headers: headers,
            /*auth:{
                username: this.username,
                password: this.password
            }*/
        }).then(response => {
            console.log(JSON.stringify(user));
            state.user = user;
            localStorage.setItem('user', user);
            localStorage.username = user.username;
            console.log(user.username + "peale sättimist");

        }).catch(error => {
            state.user = '';
            localStorage.removeItem('user');
        });
    }
};

export default {
    state,
    getters,
    actions,
    mutations,
}