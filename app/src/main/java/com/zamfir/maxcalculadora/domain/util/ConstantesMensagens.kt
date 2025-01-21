package com.zamfir.maxcalculadora.domain.util

object ConstantesMensagens {

    //Mensagens cadastro usuário
    const val MSG_SALARIO_VAZIO = "Campo salário não pode ser vazio."
    const val MSG_SALARIO_MENOR_QUE_MINIMO = "Salário não pode ser menor do que um salário minimo."
    const val MSG_NOME_VAZIO = "Campo nome deve ter ao menos 2 caracteres."

    //Mensagens trimestre
    const val MSG_META_VAZIA = "Campo de meta atingida não pode ser vazio."
    const val MSG_MES_CALCULO_PARCIAL_VAZIO ="Mês de admissão para calculo parcial não pode ser vazio."
    const val MSG_SEM_META = "Sem meta para calcular."
    const val MSG_VALOR_PRIMEIRO_TRIMESTRE_INVALIDO = "O valor atingido no 1º trimeste não pode ser maior do que 25% do salário total."
    const val MSG_VALOR_SEGUNDO_TRIMESTRE_INVALIDO = "O valor atingido no 2º trimeste não pode ser maior do que 50% do salário total."
    const val MSG_VALOR_TERCEIRO_TRIMESTRE_INVALIDO = "O valor atingido no 3º trimeste não pode ser maior do que 75% do salário total."
    const val MSG_META_ATINGIDA_ACIMA_100 = "A meta trimestral não pode ser maior que 100%"

    //Mensagens Ferias
    const val MSG_QNT_DIAS_MENOR_QUE_CINCO = "A quantidade de dias não pode ser menor do que 5."
    const val MSG_QNT_DIAS_MAIOR_QUE_TRINTA = "A quantidade de dias não pode ser maior do que 30."
    const val MSG_ABONO_PECUNIARIO = "Para que o abono pecuniário possa ser solicitado, é necessário tirar 30 dias de férias."

    //Mensagem Anual
    const val MSG_CAMPO_META_VAZIO = "Campo de meta não pode ser vazio."
    const val MSG_MES_ADMISSAO_VAZIO = "Mês de admissão para calculo parcial não pode ser vazio."
}