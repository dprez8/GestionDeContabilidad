<template>
    <div class="login-container d-flex w-100 h-100 justify-content-center">
        <div class="login-centerer h-100 d-flex align-items-center p-3 text-center">
            <div>
                <img class="mb-4" src="../assets/gesoc_logo.png" alt="" width="150" height="150">
                <h3>Por favor inicia sesión</h3>
                <loginForm @loginSuccess="loginRedirect"></loginForm>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios'
import loginForm from '../components/loginForm.vue'
import jwt_decode from "jwt-decode";

export default {
    methods: {
        loginRedirect() {
            var token = sessionStorage.getItem('token');
            if(token) {
                var tokenDecoded = jwt_decode(token);
                switch (tokenDecoded.rol) {
                    case 'Estandar': 
                        this.$router.push("/");
                        break;
                    case 'Administrador':
                        this.$router.push("/admin");
                        break;
                }
            }
        }
    },
    mounted() {
        this.loginRedirect();
    },
    components: {
        "loginForm": loginForm
    }
}
</script>

<style>
    html, body {
        width: 100%;
        height: 100%;
    }
    .login-centerer {
        max-width: 600px;
    }
</style>