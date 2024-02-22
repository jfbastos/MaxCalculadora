package com.zamfir.maxcalculadora.domain.exception

class TrimestralException(mensagem : String) : Exception(mensagem)
class CadastroException(mensagem : String) : Exception(mensagem)
class MetaException(mensagem : String) : Exception(mensagem)


//region Exceptions Férias
class QuantidadeDiasException(mensagem : String) : Exception(mensagem)
class AbonoPecuniarioException(mensagem : String) : Exception(mensagem)
//endregion