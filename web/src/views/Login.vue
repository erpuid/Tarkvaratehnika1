<template>
    <div class="login">
        <h1>Login</h1>
        <form id="login" v-on:submit.prevent="login">
            <input type='text' placeholder='username' v-model='username'>
            <input type='password' placeholder='password' v-model='password'>
            <button class="ui primary button">
                Submit
            </button>
        </form>
        <!--<h1>If you don't have an account...</h1>-->
        <!--<form @submit="SignupForm" method="post">-->
        <!--<table>-->
        <!--<tr>-->
        <!--<td><label>First name: </label></td>-->
        <!--<td><input type="text" name="firstName" v-model="firstName" /></td>-->
        <!--</tr>-->
        <!--<tr>-->
        <!--<td><label>Last name: </label></td>-->
        <!--<td><input type="text" name="lastName" v-model="lastName" /></td>-->
        <!--</tr>-->
        <!--<tr>-->
        <!--<td><label>Email: </label></td>-->
        <!--<td><input type="email" name="signUpEmail" v-model="signEmail" /></td>-->
        <!--</tr>-->
        <!--<tr>-->
        <!--<td><label>Password: </label></td>-->
        <!--<td><input type="password" name="signUpPass" v-model="signPassword" /></td>-->
        <!--</tr>-->
        <!--<tr>-->
        <!--<td><input type="submit" class="submit" name="signSubmit" value="Confirm" /></td>-->
        <!--</tr>-->
        <!--</table>-->
        <!--</form>-->
    </div>
</template>

<script>
    import {AUTH_REQUEST} from "../store/constants";
    export default {
        name: 'login',
        data() {
            return {
                username: '',
                password: ''
            }
        },
        methods: {
            login() {
                // if (this.username && this.password) {
                //     return true;
                // }
                //
                // this.errors = [];
                //
                // if (!this.username) {
                //     this.errors.push('Username required.');
                // }
                // if (!this.password) {
                //     this.errors.push('Password required.');
                // }

                const { username, password } = this;
                this.$store.dispatch(AUTH_REQUEST, {username, password}).then(() => {
                        this.$router.push('/about');
                    }
                )
            },
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