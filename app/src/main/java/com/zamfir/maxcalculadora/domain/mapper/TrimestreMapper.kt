package com.zamfir.maxcalculadora.domain.mapper

import com.zamfir.maxcalculadora.data.model.Trimestre
import com.zamfir.maxcalculadora.domain.model.TrimestreVO
import com.zamfir.maxcalculadora.util.convertMonetaryToDouble
import com.zamfir.maxcalculadora.util.convertToPercent

object TrimestreMapper {

    fun voToModel(trimestreVo: TrimestreVO) : Trimestre{
       return Trimestre(
            valorPrimeiroTrimestre = trimestreVo.valorPrimeiroTrimestre.convertMonetaryToDouble(),
            valorSegundoTrimestre = trimestreVo.valorSegundoTrimestre.convertMonetaryToDouble(),
            valorTerceiroTrimestre = trimestreVo.valorTerceiroTrimestre.convertMonetaryToDouble(),
            valorQuartoTrimestre = trimestreVo.valorQuartoTrimestre.convertMonetaryToDouble(),
            metaAtingida = trimestreVo.metaAtingida.convertToPercent(),
            isCalculoParcial = trimestreVo.isCalculoParcial,
            dataAdmissao = trimestreVo.dataAdmissao
        )
    }



}