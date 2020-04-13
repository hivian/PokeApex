package com.hivian.home.pokemon_detail.views.bindings

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.hivian.home.R
import org.jetbrains.anko.textResource
import com.hivian.model.domain.PokemonStat.Type

object DetailBinding {

    @BindingAdapter("app:statTypeText")
    @JvmStatic fun statTypeText(view: TextView, type: Type) {
        when (type) {
            Type.HP -> view.textResource = R.string.pokemon_detail_base_stat_hp
            Type.ATK -> view.textResource = R.string.pokemon_detail_base_stat_attack
            Type.DEF -> view.textResource = R.string.pokemon_detail_base_stat_defense
            Type.SP_ATK -> view.textResource = R.string.pokemon_detail_base_stat_special_defense
            Type.SP_DEF -> view.textResource = R.string.pokemon_detail_base_stat_special_attack
            Type.SPD -> view.textResource = R.string.pokemon_detail_base_stat_speed
        }
    }
}