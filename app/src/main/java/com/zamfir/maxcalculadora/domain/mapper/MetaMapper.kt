package com.zamfir.maxcalculadora.domain.mapper

import com.zamfir.maxcalculadora.data.model.Meta
import com.zamfir.maxcalculadora.domain.model.MetaVO
import com.zamfir.maxcalculadora.util.convertToPercent

object MetaMapper {

    fun voToMeta(metaVO: MetaVO) : Meta {
        return Meta(
            metaAcancada = metaVO.metaAcancada.convertToPercent(),
            mesAdmissao = metaVO.mesAdmissao,
            isCalculoParcial = metaVO.isCalculoParcial
        )
    }

}