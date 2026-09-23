package com.cooked.app.logic

object ComplimentGenerator {

    private val pool = listOf(
        "but honestly? respect the grind, king.",
        "but the dedication is real. can't fake that.",
        "not gonna lie, that's impressive in a concerning way.",
        "and yet here you are, still going. built different, fr.",
        "the commitment is iconic even if the choices aren't.",
        "you showed up. that counts for something.",
        "the focus is unmatched. weaponized, even.",
        "you've got that dawg in you. unfortunately.",
        "the resilience is a flex. a weird one. but a flex.",
        "respect. genuinely. just... also please sleep.",
        "you're really him. for better or worse.",
        "the consistency is a superpower. use it on something else too.",
        "you commit. we love that about you. mostly.",
        "the aura is real even if the schedule isn't.",
        "not gonna lie, that's a kind of talent."
    )

    fun compliment(): String = pool.random()
}
