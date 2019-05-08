<template>
    <div class="login">
        <h3>Login</h3>
        <p>{{loginError}}</p>
        <form id="login" v-on:submit.prevent="login">
            <input class="element" type='text' placeholder='username' v-model='username'>
            <br>
            <input class="element" type='password' placeholder='password' v-model='password'>
            <br>
            <button type="submit" class="submit">Submit</button>
        </form>

        <form id="register" v-on:submit.prevent="register">
            <h3>If you don't have an account</h3>
            <p>{{errorMessage}}</p>
            <table class="login_master">
                <tr>
                    <td><label>Username</label></td>
                </tr>
                <tr>
                    <td><input type="text" name="username" v-model="regUsername" /></td>
                </tr>
                <tr>
                    <td><label>Password</label></td>
                </tr>
                <tr>
                    <td><input type="password" name="regPassword" v-model="regPassword" /></td>
                </tr>
                <tr>
                    <td><label>Confirm Password</label></td>
                </tr>
                <tr>
                    <td><input type="password" name="confirmPass" v-model="confirmPass" /></td>
                </tr>
                <tr>
                    <td><button type="submit" class="submit">Submit</button></td>
                </tr>
            </table>
        </form>
    </div>
</template>

<script>
    import {AUTH_REQUEST} from "../store/constants";
    import axios from "axios";

    export default {
        name: 'login',
        data() {
            return {
                username: '',
                password: '',
                regUsername: '',
                regPassword: '',
                confirmPass: '',
                errorMessage: '',
                user: {
                    userName: '',
                    password: '',
                },
                loginError: ''
            }
        },
        methods: {
            login() {
                const { username, password } = this;
                this.$store.dispatch("authRequest", {username, password}).then(response => {
                    this.$bus.$emit('logged', 'User logged');
                    this.$router.push('/');
                }).catch(err => {
                    this.loginError = 'Try again!';
                });
            },
            register() {
                if (this.regPassword === this.confirmPass) {
                    this.user.userName = this.regUsername;
                    this.user.password =  this.regPassword;
                    axios.post('http://localhost:8080/register', {
                        username: this.user.userName,
                        password: this.user.password
                    }).then(response => {
                        console.log(response);
                        this.errorMessage = response.data;
                    });
                } else {
                    this.errorMessage = 'Passwords do not match!'
                }
            }
        }
    }
</script>

<style scoped>
    .login {
        text-align: center;
        margin-left: auto;
        margin-right: auto;
        height: 100%;
    }
    #register {
        margin-top: 50px;

    }
    input {
        text-align: center;
        margin: 0 0 5px 0;
    }
    .login_master {
        text-align: center;
        display: inline-block;
    }
</style>