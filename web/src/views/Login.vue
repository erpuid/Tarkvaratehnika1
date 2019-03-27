<template>
    <div class="login">
        <h1>Login</h1>
        <form id="login" v-on:submit.prevent="login">
            <input type='text' placeholder='username' v-model='username'>
            <input type='password' placeholder='password' v-model='password'>
            <button class="button">
                Submit
            </button>
        </form>
        <h1>If you don't have an account</h1>
        <h2> {{errorMessage}}</h2>
        <form id="register" v-on:submit.prevent="register">
        <table>
        <tr>
        <td><label>Username: </label></td>
        <td><input type="text" name="username" v-model="regUsername" /></td>
        </tr>
        <tr>
        <td><label>Password: </label></td>
        <td><input type="text" name="regPassword" v-model="regPassword" /></td>
        </tr>
        <tr>
        <tr>
        <td><label>Confirm Password: </label></td>
        <td><input type="text" name="confirmPass" v-model="confirmPass" /></td>
        </tr>
        <tr>
        <td><button class="button">
            Submit
        </button> </td>
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
                }
            }
        },
        methods: {
            login() {
                const { username, password } = this;
                this.$store.dispatch(AUTH_REQUEST, {username, password}).then(() => {
                        this.$router.push('/about');
                    }
                )
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
        font-family: 'Oswald', sans-serif;
    }

    .submit {
        font-weight: bold;
        font-family: 'Oswald', sans-serif;
        font-size: 16px;
        height: 35px;
        width: 70px;
        padding-right: 5px;
        padding-left: 5px;
        background-color: #ccffcc;
        border-color: #66ff66;
    }

    .submit:hover {
        background-color: #66ff66;
        border-color: #66ff66;
    }

    * {
        margin-top: 10px;
    }

</style>