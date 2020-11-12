<template>
    <div>
        <b-overlay spinner-variant="light" variant="primary" rounded="sm" :show="loginLoading" no-wrap></b-overlay>
        <form @submit.prevent="loginAttempt" class="pt-2">
            <b-collapse :visible="userState == false">
                <p class="text-danger text-left m-0"><span>Completa el campo usuario</span></p>
            </b-collapse>
            <b-form-input class="mb-2" v-model="user" :state="userState" type="text" placeholder="Usuario"></b-form-input>

            <b-collapse :visible="passState == false">
                <p class="text-danger text-left m-0"><span>Completa el campo contraseña</span></p>
            </b-collapse>
            <b-form-input v-model="pass" :state="passState" type="password" placeholder="Contraseña"></b-form-input>

            <b-collapse :visible="loginState == false" class="mt-2">
                <p class="text-danger text-left m-0"><span>Usuario y/o contraseña no coinciden</span></p>
            </b-collapse>
            <b-button variant="primary" type="submit" class="mt-4">Iniciar Sesión</b-button>
        </form>
    </div>
</template>

<script>
import {capitalizeFirstLetter, getCookie, RequestHelper} from '../util/utils.js'
import axios from 'axios'

export default {
    data() {
        return {
            user: "",
            pass: "",
            loginData: null,
            loginState: null,
            userState: null,
            passState: null,
            loginLoading: false
        }
    },
    methods: {
        loginAttempt() {
            this.loginState = null;
            this.userState = null;
            this.passState = null;

            if(this.user == "") {
                this.userState = false;
            } 
            if(this.pass == "") {
                this.passState = false;
            }

            if(this.user != "" && this.pass != "") {
                this.loginLoading = true;

                var loginData = {
                    username: this.user,
                    password: this.pass
                }

                RequestHelper.post('/api/login', loginData, {
                    success: (data) => {
                        document.cookie = `username=${data.nombre}; path=/`;
                        document.cookie = `organizacion=${data.organizacion.razonSocial}; path=/`;
                        this.loginSuccess();
                    },
                    empty: () => {
                        this.loginState = false;
                    },
                    error: (error) => {
                        this.$bvToast.toast(`Ocurrió un error al iniciar sesión`, {
                            title: 'Error',
                            variant: 'danger',
                            toaster: 'b-toaster-bottom-right',
                            solid: true,
                            appendToToast: true
                        });
                    },
                    allways: () => {
                        this.loginLoading = false;
                    }
                });
            }
        },
        loginSuccess() {
            this.$emit('loginSuccess');
        }
    }
}
</script>

<style>

</style>