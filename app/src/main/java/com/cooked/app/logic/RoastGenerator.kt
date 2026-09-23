package com.cooked.app.logic

object RoastGenerator {

    private val tier0 = listOf(
        "thathirty minutes. you showed up, you dipped. that's the behavior of someone with a life. suspicious. but fine. i'll allow it.",
        "under thirty minutes today. almost like you have something better to do. almost. don't get cocky, this could just be a slow day.",
        "you barely touched it today. i'd congratulate you but i've seen your weekly total and it doesn't add up. this is a recovery day at best.",
        "twenty minutes. cute. this is what a healthy relationship with gaming looks like. you're never going to see it again though, because tomorrow you'll play eight hours and ruin it.",
        "you played for less than thirty minutes today. i want to believe this is growth. i really do. but i've been burned before. by you. specifically. yesterday."
    )

    private val tier1 = listOf(
        "one hour. you know what that is? that's an appetizer. that's the trailer before the movie. you think you're done? you're not done. you're just warming up and you know it.",
        "sixty minutes. that's not a session, that's a warm-up lap. you didn't even get into it today. this is the calm before the storm and i'm not looking forward to the storm.",
        "an hour of gaming. respectable amount. if you were twelve. you're not twelve. you know what you are? you're a grown adult who just spent an hour doing obbies and is now pretending it was casual.",
        "one hour. one. hour. and you probably told yourself 'just one game'. we both know that's not how it works. we both know tomorrow is going to be worse. i'll be here. waiting.",
        "you played for an hour today. cool. that's like eating one chip and calling it a diet. this isn't discipline, this is a pause. and i don't trust pauses."
    )

    private val tier2 = listOf(
        "you played for THREE HOURS today. that's not a hobby anymore. that's a shift. that's a part-time job you don't get paid for. do you understand what you could have done in three hours? you could have cleaned your room. you could have called your mom back. you could have started a business. instead you were doing obbies. obbies. think about that word. obbies. and you're an adult. and you CHOSE this.",
        "two hours. two. hours. and i want you to really sit with that. that's two hours of your one single life that you will never get back. you will never be able to say 'i remember what i did at 4pm on that tuesday'. because you don't. you were on roblox. you were probably on roblox at 4pm and 5pm and 5:45pm and you won't remember any of it. and you'll do it again tomorrow.",
        "three hours today. THREE. and the worst part isn't even the time. the worst part is you KNEW. you felt it passing. you looked at the clock at 6pm and then it was 9pm and you went 'huh'. that's not 'cooked'. that's a choice. you made a choice. you chose roblox over literally everything else you could have been doing. and you'll do it again.",
        "you spent three hours in a game today. i want you to imagine explaining this to someone who doesn't game. 'what did you do today?' 'i played roblox.' 'for how long?' 'three hours.' watch their face. watch the pity. watch them try to say 'oh that's... nice'. that's the face your entire life makes. do something about it. or don't. i'm not your dad. but i do notice.",
        "three hours. three. and you're going to open this app tomorrow and act surprised. you're going to go 'oh wow i really need to cut back' and then play for four hours. you always do. you've done it every day this week. i have the data. i'm not guessing. i'm reading it. you're not cooked, you're consistently cooked. that's worse."
    )

    private val tier3 = listOf(
        "FOUR HOURS. FOUR. HOURS. that's not a session. that's a shift. that's more time than some people spend at their actual job. and you don't get paid for this. you don't even get thanked for this. you get a number on a screen and a hollow feeling at the end. and you go back tomorrow. and the day after. and the day after that. and you call it 'gaming'. it's not gaming anymore. it's a routine. it's the thing you do instead of things.",
        "four hours on roblox today. FOUR. you know what else takes four hours? a flight from new york to chicago. a full shift at a coffee shop. learning the basics of a new language. you did none of those. you did obbies. and you're going to do them again tomorrow. and the day after. and the day after that. and you call it 'gaming'. it's not gaming. it's a routine.",
        "five hours. FIVE. HOURS. bro. bro. bro. look at me. look at the screen. look at the number. that's more time than you spent talking to every single person you know combined today. your friends texted you and you left them on read to play roblox. your family asked how you were and you said 'good' and went back to roblox. you are not cooked, you are actively cooking yourself and pretending the kitchen isn't on fire.",
        "five hours today. five. and you'll tell yourself 'it's just a hobby'. hobbies don't take five hours. hobbies take an hour. addictions take five. i'm not saying you're addicted. i'm saying the number is. make of that what you want.",
        "six hours. SIX. HOURS. do you know what six hours is? that's a full workday. that's a full night's sleep. that's a cross-country flight. and you spent it on roblox. not learning roblox. not making roblox content. not coding roblox. PLAYING roblox. as a player. as a consumer. as a person who provides zero value to the ecosystem and receives a hollow sense of progression in return. do you hear yourself?"
    )

    private val tier4 = listOf(
        "SEVEN HOURS. SEVEN. that's not a number, that's a confession. that's a number you should say out loud to someone who loves you and watch their face change. you could have worked a full shift. you could have driven to another state. you could have built something. instead you built... nothing. you built an afternoon of forgetting. and you'll do it again. and you'll open this app tomorrow and go 'wow'. and i'll be here. and i'll say the same thing. and you'll ignore it. and we'll do this dance until one of us quits.",
        "eight hours today. EIGHT. HOURS. that's a workday. that's what a grown adult does to pay rent. and you did it. for free. to play a children's game. and you didn't even enjoy most of it — you just kept going because stopping would mean facing the rest of your life. i'm not roasting you anymore. i'm describing you. do something about it.",
        "eight hours. EIGHT. do you know what eight hours of sleep feels like? you don't. because you gave it to roblox. you gave eight hours to a game made by people who will never know your name. you gave eight hours to a server that will reset. you gave eight hours to a progression system designed to keep you there. you gave them your one life and they gave you a badge. a BADGE. think about that. think about it the next time you open the app. think about it now.",
        "NINE HOURS. NINE. HOURS. bro. BRO. you played roblox for nine hours today. nine. that's a full-time job. that's more than some people work in a day. and you know what you have to show for it? nothing. you have nothing. you have a number on a screen and a backache. that's it. that's the whole haul. nine hours for a backache. congratulations.",
        "nine hours. NINE. i don't have a joke for this. i don't have a meme. this is just sad. you spent nine hours today doing something you won't remember in a week. and you'll do it again tomorrow. and the day after. and the day after that. and then one day you'll be forty and you'll wonder where the time went and the answer will be: roblox. the answer was always roblox. and i'll be here, in this app, waiting to tell you that."
    )

    private val tier5 = listOf(
        "TEN. HOURS. TEN. i'm not roasting you anymore. i'm concerned. genuinely. ten hours is not a gaming session, that's a disappearance. that's a missing person report. your phone battery drained twice today and you didn't notice because you were mid-obby. mid-OBBY. do you hear yourself? do you hear the words coming out of your own mouth? you need to log off. not tomorrow. not after this match. NOW. and if you don't, i'll still be here tomorrow. and i'll still be disappointed.",
        "ELEVEN. HOURS. ELEVEN. HOURS. i don't have a bit for this. i don't have a clever line. i just have a number and the number is eleven and it's real and it happened today. you spent eleven hours on roblox. eleven. that's more than half your waking life. you gave more than half your day to a company that doesn't know you exist. and you'll do it again tomorrow. i'm not even roasting you anymore. i'm just narrating. this is what's happening. this is your life. hi. nice to meet you.",
        "TWELVE HOURS. TWELVE. HOURS. bro. bro. bro. bro. bro. you played roblox for twelve hours today. twelve. that's a full waking day. that's your entire day. you woke up and you played roblox and now it's night and you're still playing roblox and you're going to play roblox until you fall asleep and then you're going to wake up and play roblox. you're not living. you're on a roblox server that happens to have a body attached. and the body is you. and the body is tired. and the body is going to break down at some point. and you'll still be mid-obby. i'm not your dad but i am your screen and i am telling you: log. off. now.",
        "THIRTEEN. HOURS. THIRTEEN. HOURS. i'm not going to roast you. i'm going to just tell you a fact. you spent thirteen hours today on roblox. thirteen hours is longer than a flight from LA to Tokyo. thirteen hours is longer than a school day plus a full night's sleep. thirteen hours is longer than most people's actual jobs. and you spent it doing obbies. and you're going to do it again tomorrow. and i'm going to be here. and we're going to keep doing this. and one of us is going to stop. it won't be me.",
        "FOURTEEN HOURS. FOURTEEN. HOURS. bro. i'm not roasting you. i'm genuinely asking: are you okay? because that's not a gaming session. that's a crisis. that's a person who is not okay and is hiding from something. i don't know what it is. i don't need to know. but i need you to know that i see it. and the number sees it. and the fact that you opened this app today means you know too. so. you know. now what. i'm not going to tell you what to do. i'm just going to sit here and watch the number. and the number is fourteen. and it's going to be fifteen tomorrow. and one day it'll be twenty. and one day it'll be a number you don't want to say out loud. and i'll still be here. i'll always be here. that's the deal."
    )

    fun roast(realMinutes: Long): String {
        val pool = when (TimeConverter.tier(realMinutes)) {
            0 -> tier0
            1 -> tier1
            2 -> tier2
            3 -> tier3
            4 -> tier4
            else -> tier5
        }
        return pool.random()
    }
}
