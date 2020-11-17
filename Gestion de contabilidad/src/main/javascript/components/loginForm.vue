<template>
    <div>
        <b-overlay spinner-variant="light" variant="primary" rounded="sm" :show="loginLoading" no-wrap></b-overlay>
        <form @submit.prevent="loginAttempt" class="pt-2">
            <b-collapse :visible="userState == false">
                <p class="text-danger text-left m-0"><span>Completa el campo usuario</span></p>
            </b-collapse>
            <b-form-input class="mb-2 border-secondary" v-model="user" :state="userState" type="text" placeholder="Usuario"></b-form-input>

            <b-collapse :visible="passState == false">
                <p class="text-danger text-left m-0"><span>Completa el campo contraseña</span></p>
            </b-collapse>
            <b-input-group>
                <b-form-input v-model="pass" :state="passState" :type="showPassword ? 'text' : 'password'" class="border-secondary" placeholder="Contraseña"></b-form-input>
                <template #append>
                    <b-button variant="outline-secondary" @click="showPassword = !showPassword"><b-icon :icon="showPassword ? 'eye-slash' : 'eye'"/></b-button>
                </template>
            </b-input-group>

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
            loginLoading: false,
            showPassword: false
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

                RequestHelper.post('/api/auth/login', loginData, {
                    success: (data) => {
                        this.$emit('loginSuccess');
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
                    always: () => {
                        this.loginLoading = false;
                    }
                });
            }
        }
    }
}
</script>

<style>

</style>