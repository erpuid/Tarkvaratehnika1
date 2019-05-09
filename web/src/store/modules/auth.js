import {AUTH_LOGOUT, AUTH_REQUEST, BASE_URL} from "../constants";
import axios from "axios";
import store from "../index";

const state = { token: localStorage.getItem('token') || '' };

const getters = {
    isAuthenticated: state => !!state.token
};

const actions = {
    authRequest: ({commit, dispatch}, user) => {
        return new Promise((resolve, reject) => {
            const params = new URLSearchParams();
            params.append('grant_type', 'password');
            params.append('username', user.username);
            params.append('password',user.password);
            axios({
                method: 'post',
                url: BASE_URL + 'oauth/token',
                auth: {username: 'my-trusted-client', password: 'secret'},
                headers: {"Content-type": "application/x-www-form-urlencoded; charset=utf-8"},
                data: params
            }).then(response => {
                // console.log(response.data.access_token);
                // console.log(response.data);
                localStorage.setItem('token', response.data.access_token);
                localStorage.setItem('username', user.username);
                resolve(response);
            }).catch(err => {
                localStorage.removeItem('token');
                localStorage.removeItem('username');
                reject(err);
            });
        })
    },
    [AUTH_LOGOUT]: ({commit, dispatch}) => {
        return new Promise((resolve, reject) => {
            commit(AUTH_LOGOUT);
            resolve()
        })
    }
};
axios.interceptors.request.use(request => {
    // console.log('Starting Request', request);
    return request
});

const mutations = {
    [AUTH_LOGOUT]: () => {
        axios.get('http://localhost:8080/logout/'+localStorage.getItem('token')).then(response => {
            localStorage.removeItem('token');
            localStorage.removeItem('username');
        });
    }
};

export default {
    state,
    getters,
    actions,
    mutations,
}