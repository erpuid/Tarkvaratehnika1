<template>
    <div class="login">
        <h1>Login</h1>
        <form @submit="LoginForm" method="post">
            <label>Email </label>
            <input type="email" name="email" v-model="email" />
            <br/>
            <label>Password </label>
            <input type="password" name="password" v-model="password">
            <br/>
            <input type="submit" name="submit" value="Submit" />
        </form>
        <h1>If you don't have an account...</h1>
        <form @submit="SignupForm" method="post">
            <label>First name </label>
            <input type="text" name="firstName" v-model="firstname" />
            <br>
            <label>Last name </label>
            <input type="text" name="lastName" v-model="lastname" />
            <br>
            <label>Email </label>
            <input type="email" name="signUpEmail" v-model="signemail" />
            <br>
            <label>Password </label>
            <input type="password" name="signUpPass" v-model="signPassword" />
            <br>
            <input type="submit" name="signSubmit" value="Confirm" />
        </form>
    </div>
</template>

<script>

    export default {
        name: 'login',
        methods: {
            LoginForm: function (e) {
                if (this.email && this.password) {
                    return true;
                }

                this.errors = [];

                if (!this.email) {
                    this.errors.push('Email required.');
                }
                if (!this.password) {
                    this.errors.push('Password required.');
                }

                e.preventDefault();
            },

            SignupForm: function (e) {

            }
        }
    }
</script>

<style scoped>
    * {
        margin-top: 10px;
    }

</style>