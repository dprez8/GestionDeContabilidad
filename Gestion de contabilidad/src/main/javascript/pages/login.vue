<template>
    <div class="login-container d-flex w-100 h-100 justify-content-center">
        <div class="login-centerer p-3 text-center">
            <img class="mb-4" src="../assets/gesoc_logo.png" alt="" width="150" height="150">
            <h3>Por favor inicia sesión</h3>
            <loginForm @loginSuccess="loginSuccess"></loginForm>
        </div>
    </div>
</template>

<script>
import loginForm from '../components/loginForm.vue'

export default {
    methods: {
        loginSuccess() {
            this.$router.push("/");
        }
    },
    components: {
        "loginForm": loginForm
    }
}
</script>

<style>
    .login-centerer {
        max-width: 600px;
    }
</style>