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
import {capitalizeFirstLetter, getCookie} from '../util/utils.js'
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

            console.log(`Usuario: ${this.user}`);
            console.log(`Contraseña: ${this.pass}`);

            if(this.user == "") {
                this.userState = false;
            } 
            if(this.pass == "") {
                this.passState = false;
            }

            if(this.user != "" && this.pass != "") {
                
                axios
                    .post('http://gesoc.ddns.net/api/login', {
                        username: this.user,
                        password: this.pass
                    })
                    .then(response => {
                        var data = response.data;
                        if(data.code == 200) {
                            this.loginSuccess();
                            console.log(response);
                        } else if (data.code == 404) {
                            this.loginState = false;
                        } else {
                            //app.createCommonErrors(data);
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