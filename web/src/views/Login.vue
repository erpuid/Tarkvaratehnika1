<template>
    <div class="login">
        <form @submit="CheckForm" method="post">
            <label>Email</label>
            <input type="email" name="email" v-model="email" />
            <br/>
            <label>Password</label>
            <input type="password" name="password" v-model="password">
            <br/>
            <input type="submit" name="submit" value="Submit" />
        </form>
    </div>
</template>

<script>

    export default {
        name: 'login',
        methods: {
            CheckForm: function (e) {
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
            }
        }
    }
</script>

<style scoped>
    * {
        margin-top: 10px;
    }

</style>