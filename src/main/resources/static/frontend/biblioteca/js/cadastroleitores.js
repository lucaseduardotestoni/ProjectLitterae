//Pega o id do usuario logado
//var idUsuario = sessionStorage.getItem("idUsuario");
var idUsuario = 100001

$('#cep').mask('00000-000');
// Limpa os dados de Endereço do Formulário
const limparFormulario = (endereco) => {
    document.getElementById('rua').value = '';
    document.getElementById('bairro').value = '';
    document.getElementById('cidade').value = '';
    document.getElementById('estado').value = '';
}

//Preenche os Dados do Endereço Conforme a resposta do Viacep
const preencherFormulario = (endereco) => {
    document.getElementById('rua').value = endereco.logradouro;
    document.getElementById('bairro').value = endereco.bairro;
    document.getElementById('cidade').value = endereco.localidade;
    document.getElementById('estado').value = endereco.uf;
}


const eNumero = (numero) => /^[0-9]+$/.test(numero);

const cepValido = (cep) => cep.length == 8 && eNumero(cep);

const pesquisarCep = async () => {
    limparFormulario();
//------------------------------------------------------------------------------
    //Chama Api do via cep e Valida ela
    const cep = document.getElementById('cep').value.replace("-", "");
    const url = `https://viacep.com.br/ws/${cep}/json/`;
    if (cepValido(cep)) {
        const dados = await fetch(url);
        const endereco = await dados.json();
        if (endereco.hasOwnProperty('erro')) {
            document.getElementById('endereco').value = 'CEP não encontrado!';
        } else {
            preencherFormulario(endereco);
        }
    } else {
        document.getElementById('endereco').value = 'CEP incorreto!';
    }

}
document.getElementById('cep')
    .addEventListener('focusout', pesquisarCep);

//--------------------------------------------------------------------------
//Bloqueia a atualização do Site
const form = document.getElementById('some-form')
form.addEventListener('submit', e => {
    e.preventDefault()
})
//--------------------------------------------------------------------------
//Chama a função onclick do botão
function btnEnviar(cont) {
    cont = 0
    $('.input-form').each(function(){
        if(this.value != ''){
            cont = cont + 1
            if (cont == 12){
                Cadastrar();
            }else if (cont == 11 && $("#telefone").val() == ""){
                Cadastrar();
            }
        }
    });
}

//Função Assíncrona de Cadastro
async function Cadastrar() {
    //Preparando o Body para Envio ao Servidor
    var jsonCadastro = JSON.stringify({
        "id": null,
        "cpf": $("#cpf").val(),
        "nome": $("#nome").val(),
        "sobrenome": $("#sobrenome").val(),
        "enderecoUsuario": {
            "id": null,
            "cep": $("#cep").val(),
            "estado": $("#estado").val(),
            "cidade": $("#cidade").val(),
            "bairro": $("#bairro").val(),
            "rua": $("#numero").val(),
            "numero": $("#rua").val(),
            "complemento": ""
        },
        "idBiblioteca": idUsuario,
        "email": "",
        "dataNascimento": $("#data").val(),
        "telefone1": $("#celular").val(),
        "telefone2": $("#telefone").val(),
        "imagem": null,
        "tipoPerfil": 1,
        "ativo": true
    });

    //Chama Função valida Cpf
    validarCPF(cpf)
        //verifica se o Cpf é valido
        if (validarCPF(cpf) == true) {

            //faz a chamada da conexão
            fetch("http://localhost:80/usuario/cadastrar", {method: 'POST',headers:{'Content-Type': 'application/json'}, body: jsonCadastro
            })
                .then(function (resposta) {
                    //tratamento do erro exibindo mensagem
                    if (resposta.status>= 200 && resposta.status <= 300 ) {
                        document.querySelector('#alertaEr').style.color = "#0c4900";
                        document.querySelector("#alertaEr").innerHTML = "    Cadastrado com Sucesso"
                        $("#alertaEr").fadeIn();
                        setTimeout(AlertaOut, 5000)
                        setTimeout(reload, 2000)
                    }else{
                        console.log(resposta.json())
                        document.querySelector('#alertaEr').style.color="Red";
                        document.querySelector("#alertaEr").innerHTML = "Erro no Cadastro, Por Favor Contate um Administrador."
                        console.log(resposta.json())
                        $("#alertaEr").fadeIn();
                        setTimeout(AlertaOut, 5000)

                    }
                })

        }
        //Mensagem de Erro Cpf
        else {
            $("#ErrCPF").fadeIn();
        }
}

//função validação de CPF
function validarCPF(cpf) {
    //Passa valor do input
    cpf = $("#cpf").val();
    //remove qualquer traço ou ponto
    cpf = cpf.replace(/[^\d]+/g, '');
    if (cpf == '') return false;
    // Elimina CPFs invalidos conhecidos
    if (cpf.length != 11 ||
        cpf == "00000000000" ||
        cpf == "11111111111" ||
        cpf == "22222222222" ||
        cpf == "33333333333" ||
        cpf == "44444444444" ||
        cpf == "55555555555" ||
        cpf == "66666666666" ||
        cpf == "77777777777" ||
        cpf == "88888888888" ||
        cpf == "99999999999")
        return false;
    // Valida 1º digito
    var add = 0;
    for (let i = 0; i < 9; i++)
        add += parseInt(cpf.charAt(i)) * (10 - i);
    let rev = 11 - (add % 11);
    if (rev == 10 || rev == 11)
        rev = 0;
    if (rev != parseInt(cpf.charAt(9)))
        return false;
    // Valida 2º digito
    add = 0;
    for (let i = 0; i < 10; i++)
        add += parseInt(cpf.charAt(i)) * (11 - i);
    rev = 11 - (add % 11);
    if (rev == 10 || rev == 11) {
        rev = 0;
    }
    if (rev != parseInt(cpf.charAt(10))) {
        return false;
    }

    return true;
}

//Função assíncrona de saída de mensagem
async function AlertaOut() {
    $("#alertaEr").fadeOut();
}

function reload() {
    document.location.reload(true);
}