<template>
    <div class="login">
        <h3>Login</h3>
        <form id="login" v-on:submit.prevent="login">
            <input class="element" type='text' placeholder='username' v-model='username'>
            <input class="element" type='password' placeholder='password' v-model='password'>
            <button type="submit" class="submit">
                Submit
            </button>
        </form>
        <h3>If you don't have an account</h3>
        <h2> {{errorMessage}}</h2>
        <form id="register" v-on:submit.prevent="register">
        <table class="login_master">
        <tr>
        <td><label>Username: </label></td>
        <td><input type="text" name="username" v-model="regUsername" /></td>
        </tr>
        <tr>
        <td><label>Password: </label></td>
        <td><input type="password" name="regPassword" v-model="regPassword" /></td>
        </tr>
        <tr>
        <td><label>Confirm Password: </label></td>
        <td><input type="password" name="confirmPass" v-model="confirmPass" /></td>
        </tr>
        <tr>
        <td><button type="submit" class="submit">
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
                    axios.post('/register', {
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
    * {
        margin-top: 10px;
    }

</style>