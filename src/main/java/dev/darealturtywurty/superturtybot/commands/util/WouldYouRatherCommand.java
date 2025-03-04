package dev.darealturtywurty.superturtybot.commands.util;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import dev.darealturtywurty.superturtybot.core.command.CommandCategory;
import dev.darealturtywurty.superturtybot.core.command.CoreCommand;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.apache.commons.lang3.tuple.Pair;

public class WouldYouRatherCommand extends CoreCommand {
    private static final String[] QUESTIONS = {
        "Would you rather be in jail for a year {a} or lose a year off your life {b}?",
        "Would you rather always be 10 minutes late {a} or always be 20 minutes early {b}?",
        "Would you rather have one real get out of jail free card {a} or a key that opens any door {b}?",
        "Would you rather know the history of every object you touched {a} or be able to talk to animals {b}?",
        "Would you rather be married to a 10 with a bad personality {a} or a 6 with an amazing personality {b}?",
        "Would you rather be able to talk to land animals, animals that fly, {a} or animals that live under the water {b}?",
        "Would you rather have all traffic lights you approach be green {a} or never have to stand in line again {b}?",
        "Would you rather spend the rest of your life with a sailboat as your home {a} or an RV as your home {b}?",
        "Would you rather give up all drinks except for water {a} or give up eating anything that was cooked in an oven {b}?",
        "Would you rather be able to see 10 minutes into your own future {a} or 10 minutes into the future of anyone but yourself {b}?",
        "Would you rather have an easy job working for someone else {a} or work for yourself but work incredibly hard {b}?",
        "Would you rather be the first person to explore a planet {a} or be the inventor of a drug that cures a deadly disease {b}?",
        "Would you rather go back to age 5 with everything you know now {a} or know now everything your future self will learn {b}?",
        "Would you rather be able to control animals (but not humans) with your mind {a} or control electronics with your mind {b}?",
        "Would you rather have unlimited international first-class tickets {a} or never have to pay for food at restaurants {b}?",
        "Would you rather see what was behind every closed door {a} or be able to guess the combination of every safe on the first try {b}?",
        "Would you rather be an average person in the present {a} or a king of a large country 2500 years ago {b}?",
        "Would you rather be able to dodge anything no matter how fast it’s moving {a} or be able to ask any three questions and have them answered accurately {b}?",
        "Would you rather be forced to dance every time you heard music {a} or be forced to sing along to any song you heard {b}?",
        "Would you rather have all your clothes fit perfectly {a} or have the most comfortable pillow, blankets, and sheets in existence {b}?",
        "Would you rather 5% of the population have telepathy, {a} or 5% of the population have telekinesis - You are not part of the 5% that has telepathy {a} or telekinesis {b}?",
        "Would you rather be an unimportant character in the last movie you saw {a} or an unimportant character in the last book you read {b}?",
        "Would you rather move to a new city {a} or town every week {a} or never be able to leave the city {a} or town you were born in {b}?",
        "Would you rather be completely insane and know that you are insane {a} or completely insane and believe you are sane {b}?",
        "Would you rather travel the world for a year on a shoestring budget {a} or stay in only one country for a year but live in luxury {b}?",
        "Would you rather suddenly be elected a senator {a} or suddenly become a CEO of a major company (You won’t have any more knowledge about how to do either job than you do right now.) {b}?",
        "Would you rather live in virtual reality where you are all powerful {a} or live in the real world and be able to go anywhere but not be able to interact with anyone {a} or anything {b}?",
        "Would you rather have whatever you are thinking to appear above your head for everyone to see {a} or have absolutely everything you do live streamed for anyone to see {b}?",
        "Would you rather be only able to watch the few movies with a Rotten Tomatoes score of 95-100% {a} or only be able to watch the majority of movies with a Rotten Tomatoes score of 94% and lower {b}?",
        "Would you rather wake up as a new random person every year and have full control of them for the whole year {a} or once a week spend a day inside a stranger without having any control of them {b}?",
        "Would you rather know how above {a} or below average you are at everything {a} or know how above {a} or below average people are at one skill/talent just by looking at them {b}?",
        "Would you rather live until you are 200 but look like you are 200 the whole time even though you are healthy {a} or look like you are 25 all the way until you die at age 65 {b}?",
        "Would you rather be a reverse centaur {a} or a reverse mermaid/merman {b}?",
        "Would you rather your only mode of transportation be a donkey {a} or a giraffe {b}?",
        "Would you rather only be to use a fork (no spoon) {a} or only be able to use a spoon (no fork) {b}?",
        "Would you rather every shirt you ever wear be kind of itchy {a} or only be able to use 1 ply toilet paper {b}?",
        "Would you rather have edible spaghetti hair that regrows every night {a} or sweat (not sweet) maple syrup {b}?",
        "Would you rather have to read aloud every word you read {a} or sing everything you say out loud {b}?",
        "Would you rather wear a wedding dress/tuxedo every single day {a} or wear a bathing suit every single day {b}?",
        "Would you rather be unable to move your body every time it rains {a} or not be able to stop moving while the sun is out {b}?",
        "Would you rather have all dogs try to attack you when they see you {a} or all birds try to attack you when they see you {b}?",
        "Would you rather be compelled to high five everyone you meet {a} or be compelled to give wedgies to anyone in a green shirt {b}?",
        "Would you rather have skin that changes color based on your emotions {a} or tattoos appear all over your body depicting what you did yesterday {b}?",
        "Would you rather randomly time travel +/- 20 years every time you fart {a} or teleport to a different place on earth (on land, not water) every time you sneeze {b}?",
        "Would you rather there be a perpetual water balloon war going on in your city/town {a} or a perpetual food fight {b}?",
        "Would you rather have to fart loudly every time you have a serious conversation {a} or have to burp after every kiss {b}?",
        "Would you rather become twice as strong when both of your fingers are stuck in your ears {a} or crawl twice as fast as you can run {b}?",
        "Would you rather have everything you draw become real but be permanently terrible at drawing {a} or be able to fly but only as fast as you can walk {b}?",
        "Would you rather thirty butterflies instantly appear from nowhere every time you sneeze {a} or one very angry squirrel appear from nowhere every time you cough {b}?",
        "Would you rather vomit uncontrollably for one minute every time you hear the happy birthday song {a} or get a headache that lasts for the rest of the day every time you see a bird (including in pictures {a} or a video) {b}?",
        "Would you rather eat a sandwich made from 4 ingredients in your fridge chosen at random {a} or eat a sandwich made by a group of your friends from 4 ingredients in your fridge {b}?",
        "Would you rather everyone be required to wear identical silver jumpsuits {a} or any time two people meet and are wearing an identical article of clothing they must fight to the death {b}?",
        "Would you rather be a famous director {a} or a famous actor {b}?",
        "Would you rather be a practicing doctor {a} or a medical researcher {b}?",
        "Would you rather live in a cave {a} or live in a tree house {b}?",
        "Would you rather be able to control fire {a} or water {b}?",
        "Would you rather live without the internet {a} or live without AC and heating {b}?",
        "Would you rather be able to teleport anywhere {a} or be able to read minds {b}?",
        "Would you rather be unable to use search engines {a} or unable to use social media {b}?",
        "Would you rather be beautiful/handsome but stupid {a} or intelligent but ugly {b}?",
        "Would you rather be balding but fit {a} or overweight with a full head of hair {b}?",
        "Would you rather never be able to eat meat {a} or never be able to eat vegetables {b}?",
        "Would you rather have a completely automated home {a} or a self-driving car {b}?",
        "Would you rather be an amazing painter {a} or a brilliant mathematician {b}?",
        "Would you rather be famous but ridiculed {a} or be just a normal person {b}?",
        "Would you rather have a flying carpet {a} or a car that can drive underwater {b}?",
        "Would you rather never be stuck in traffic again {a} or never get another cold {b}?",
        "Would you rather have a bottomless box of Legos {a} or a bottomless gas tank {b}?",
        "Would you rather be forced to eat only spicy food {a} or only incredibly bland food {b}?",
        "Would you rather be a bowling champion {a} or a curling champion {b}?",
        "Would you rather be fantastic at riding horses {a} or amazing at driving dirt bikes {b}?",
        "Would you rather never be able to wear underwear {a} or never be able to wear anything on top of your underwear {b}?",
        "Would you rather live the next 10 years of your life in China {a} or Russia {b}?",
        "Would you rather live on the beach {a} or in a cabin in the woods {b}?",
        "Would you rather be lost in a bad part of town {a} or lost in the forest {b}?",
        "Would you rather have a horrible short-term memory {a} or a horrible long-term memory {b}?",
        "Would you rather be completely invisible for one day {a} or be able to fly for one day {b}?",
        "Would you rather never be able to use a touchscreen {a} or never be able to use a keyboard and mouse {b}?",
        "Would you rather have unlimited sushi for life {a} or unlimited tacos for life (both are amazingly delicious and can be any type of sushi/taco you want) {b}?",
        "Would you rather get one free round trip international plane ticket every year {a} or be able to fly domestic anytime for free {b}?",
        "Would you rather be able to be free from junk mail {a} or free from email spam for the rest of your life {b}?",
        "Would you rather give up bathing for a month {a} or give up the internet for a month {b}?",
        "Would you rather give up watching TV/movies for a year {a} or give up playing games for a year {b}?",
        "Would you rather never be able to drink sodas like coke again {a} or only be able to drink sodas and nothing else {b}?",
        "Would you rather have amazingly fast typing/texting speed {a} or be able to read ridiculously fast {b}?",
        "Would you rather live under a sky with no stars at night {a} or live under a sky with no clouds during the day {b}?",
        "Would you rather have free Wi-Fi wherever you go {a} or be able to drink unlimited free coffee at any coffee shop {b}?",
        "Would you rather take amazing selfies, but all of your other pictures are horrible {a} or take breathtaking photographs of anything but yourself {b}?",
        "Would you rather never get a paper cut again {a} or never get something stuck in your teeth again {b}?",
        "Would you rather never have another embarrassing fall in public {a} or never feel the need to pass gas in public again {b}?",
        "Would you rather lose your best friend {a} or all of your friends except for your best friend {b}?",
        "Would you rather it never stops snowing (the snow never piles up) {a} or never stops raining (the rain never causes floods) {b}?",
        "Would you rather never be able to leave your own country {a} or never be able to fly in an airplane {b}?",
        "Would you rather never have a toilet clog on you again {a} or never have the power go out again {b}?",
        "Would you rather earbuds and headphones never sit right on / in your ears {a} or have all music either slightly too quiet {a} or slightly too loud {b}?",
        "Would you rather be the best in the world at climbing trees {a} or the best in the world at jumping rope {b}?",
        "Would you rather never run out of battery power for whatever phone and tablet you own {a} or always have free Wi-Fi wherever you go {b}?",
        "Would you rather never have to clean a bathroom again {a} or never have to do dishes again {b}?",
        "Would you rather eat an egg with a half-formed chicken inside {a} or eat ten cooked grasshoppers {b}?",
        "Would you rather only wear one color each day {a} or have to wear seven colors each day {b}?",
        "Would you rather eat rice with every meal and never be able to eat bread {a} or eat bread with every meal and never be able to eat rice {b}?",
        "Would you rather never wear any clothes {a} or never be able to take of your clothes {b}?",
        "Would you rather travel the world for a year all expenses paid {a} or have $40,000 to spend on whatever you want {b}?",
        "Would you rather be able to go to any theme park in the world for free for the rest of your life {a} or eat for free at any drive-through restaurant for the rest of your life {b}?",
        "Would you rather be the absolute best at something that no one takes seriously {a} or be well above average but not anywhere near the best at something well respected {b}?",
        "Would you rather it be impossible for you to be woken up for 11 straight hours every day, but you wake up feeling amazing, {a} or you can be woken up normally but never feel totally rested {b}?",
        "Would you rather have everything in your house perfectly organized by a professional {a} or have a professional event company throw the best party you’ve ever been to, in your honor {b}?",
        "Would you rather have unlimited amounts of any material you want to build a house, but you have to build the house all by yourself {a} or have a famed architect design and build you a modest house {b}?",
        "Would you rather never sweat again but not be more prone to heat stroke {a} or never feel cold again but cold still physically affects you (i.e., you can freeze to death) {b}?",
        "Would you rather super sensitive taste {a} or super sensitive hearing {b}?",
        "Would you rather have constantly dry eyes {a} or a constant runny nose {b}?",
        "Would you rather never lose your phone again {a} or never lose your keys again {b}?",
        "Would you rather have out of control body hair {a} or a strong, pungent body odor {b}?",
        "Would you rather be unable to have kids {a} or only be able to conceive quintuplets {b}?",
        "Would you rather clean rest stop toilets {a} or work in a slaughterhouse for a living {b}?",
        "Would you rather lose all your money and valuables {a} or all the pictures you have ever taken {b}?",
        "Would you rather find your true love {a} or a suitcase with five million dollars inside {b}?",
        "Would you rather not be able to see any colors {a} or have mild but constant tinnitus (ringing in the ears) {b}?",
        "Would you rather have chapped lips that never heal {a} or terrible dandruff that can’t be treated {b}?",
        "Would you rather live without hot water for showers/baths {a} or live without a washing machine {b}?",
        "Would you rather be alone for the rest of your life {a} or always be surrounded by annoying people {b}?",
        "Would you rather be locked in a room that is constantly dark for a week {a} or a room that is constantly bright for a week {b}?",
        "Would you rather accidentally be responsible for the death of a child {a} or accidentally be responsible for the deaths of three adults {b}?",
        "Would you rather have everything you eat be too salty {a} or not salty enough no matter how much salt you add {b}?",
        "Would you rather never have to work again {a} or never have to sleep again (you won’t feel tired {a} or suffer negative health effects) {b}?",
        "Would you rather never use social media sites/apps again {a} or never watch another movie {a} or TV show {b}?",
        "Would you rather be fluent in all languages and never be able to travel {a} or be able to travel anywhere for a year but never be able to learn a word of a different language {b}?",
        "Would you rather be put in a maximum-security federal prison with the hardest of the hardened criminals for one year {a} or be put in a relatively relaxed prison where wall street types are held for ten years {b}?",
        "Would you rather have everything on your phone right now (browsing history, photos, etc.) made public to anyone who searches your name {a} or never use a cell phone again {b}?",
        "Would you rather be an amazing artist but not be able to see any of the art you created {a} or be an amazing musician but not be able to hear any of the music you create {b}?",
        "Would you rather have everyone laugh at your jokes but not find anyone else’s jokes funny {a} or have no one laugh at your jokes but you still find other people’s jokes funny {b}?",
        "Would you rather wake up in the middle of an unknown desert {a} or wake up in a rowboat on an unknown body of water {b}?",
        "Would you rather always have a great body for your entire life but have slightly below average intelligence {a} or have a mediocre body for your entire life but be slightly above average in intelligence {b}?",
        "Would you rather be in debt for $100,000 {a} or never be able to make more than $3,500 a month {b}?",
        "Would you rather have the police hunting you for a murder you didn’t commit {a} or a psychopathic clown hunting you {b}?",
        "Would you rather be constantly tired no matter how much you sleep {a} or constantly hungry no matter how much you eat {b}?",
        "Would you rather live a comfortable and peaceful life in the woods in a small cabin without much human interaction {a} or a life full of conflict and entertainment in a mansion in a city {b}?",
        "Would you rather walk around work {a} or school for the whole day without realizing there is a giant brown stain on the back of your pants {a} or realize the deadline for that important paper/project was yesterday, and you are nowhere near done {b}?",
        "Would you rather be so afraid of heights that you can’t go to the second floor of a building {a} or be so afraid of the sun that you can only leave the house on rainy days {b}?",
        "Would you rather get tipsy from just one sip of alcohol and ridiculously drunk from just one alcoholic drink {a} or never get drunk no matter how much alcohol you drank {b}?",
        "Would you rather be hired for a well-paying job that you lied to get and have no idea how to do {a} or be about to give the most important presentation of your life but you can’t remember any of the material you prepared {b}?",
        "Would you rather be feared by all {a} or loved by all {b}?",
        "Would you rather sell all of your possessions {a} or sell one of your organs {b}?",
        "Would you rather be infamous in history books {a} or be forgotten after your death {b}?",
        "Would you rather be reincarnated as a fly {a} or just cease to exist after you die {b}?",
        "Would you rather never get angry {a} or never be envious {b}?",
        "Would you rather have a horribly corrupt government {a} or no government {b}?",
        "Would you rather be held in high regard by your parents {a} or your friends {b}?",
        "Would you rather be poor but help people {a} or become incredibly rich by hurting people {b}?",
        "Would you rather humans go to the moon again {a} or go to mars {b}?",
        "Would you rather know the uncomfortable truth of the world {a} or believe a comforting lie {b}?",
        "Would you rather die in 20 years with no regrets {a} or die in 50 years with many regrets {b}?",
        "Would you rather be transported permanently 500 years into the future {a} or 500 years into the past {b}?",
        "Would you rather donate your body to science {a} or donate your organs to people who need them {b}?",
        "Would you rather be famous when you are alive and forgotten when you die {a} or unknown when you are alive but famous after you die {b}?",
        "Would you rather go to jail for 4 years for something you didn’t do {a} or get away with something horrible you did but always live in fear of being caught {b}?",
        "Would you rather live in the wilderness far from civilization with no human contact {a} or live on the streets of a city as a homeless person {b}?",
        "Would you rather live your entire life in a virtual reality where all your wishes are granted {a} or just in the normal real world {b}?",
        "Would you rather have a horrible job, but be able to retire comfortably in 10 years {a} or have your dream job, but have to work until the day you die {b}?",
        "Would you rather lose all of your memories from birth to now {a} or lose your ability to make new long-term memories {b}?",
        "Would you rather always be able to see 5 minutes into the future {a} or always be able to see 100 years into the future {b}?",
        "Would you rather be forced to kill one innocent person {a} or five people who committed minor crimes {b}?",
        "Would you rather work very hard at a rewarding job {a} or hardly have to work at a job that isn’t rewarding {b}?",
        "Would you rather have a criminal justice system that actually works and is fair {a} or an administrative branch that is free of corruption {b}?",
        "Would you rather have real political power but be relatively poor {a} or be ridiculously rich and have no political power {b}?",
        "Would you rather have the power to gently nudge anyone’s decisions {a} or have complete puppet master control of five people {b}?",
        "Would you rather live in a utopia as a normal person {a} or in a dystopia but you are the supreme ruler {b}?",
        "Would you rather snitch on your best friend for a crime they committed {a} or go to jail for the crime they committed {b}?",
        "Would you rather be born again in a totally different life {a} or born again with all the knowledge you have now {b}?",
        "Would you rather all conspiracy theories be true {a} or live in a world where no leaders really know what they are doing {b}?",
        "Would you rather know all the mysteries of the universe {a} or know every outcome of every choice you make {b}?",
        "Would you rather spend two years with your soul mate only to have them die and you never love again {a} or spend your life with someone nice you settled for {b}?",
        "Would you rather have all corporations know all of your computer usage {a} or the government know all of your computer usage {b}?",
        "Would you rather inherit 20 million dollars when you turn 18 {a} or spend the time earning 50 million dollars through your hard work {b}?",
        "Would you rather the general public think you are a horrible person, but your family is very proud of you, {a} or your family thinks you are a horrible person, but the general public be very proud of you {b}?",
        "Would you rather fight for a cause you believe in, but doubt will succeed {a} or fight for a cause that you only partially believe in but have a high chance of your cause succeeding {b}?",
        "Would you rather be famous for inventing a deadly new weapon {a} or invent something that helps the world but someone else gets all the credit for inventing it {b}?",
        "Would you rather live in a haunted house where the ghosts ignore you and do their own thing {a} or be a ghost in a house living out a pleasant and uneventful week from your life again and again {b}?",
        "Would you rather write a novel that will be widely considered the most important book in the past 200 years, but you and the book will only be appreciated after your death {a} or be the most famous erotica writer of your generation {b}?",
        "Would you rather have done something horribly embarrassing and only your best friend knows {a} or not done something horribly embarrassing, but everyone except your best friend thinks you did it {b}?",
        "Would you rather your shirts be always two sizes too big {a} or one size too small {b}?",
        "Would you rather find five dollars on the ground {a} or find all your missing socks {b}?",
        "Would you rather only have underwear 3 sizes bigger {a} or only have underwear 3 sizes smaller {b}?",
        "Would you rather have one nipple {a} or two belly buttons {b}?",
        "Would you rather eat a ketchup sandwich {a} or a Siracha sandwich {b}?",
        "Would you rather use a push lawnmower with a bar that is far too high {a} or far too low {b}?",
        "Would you rather eat a box of dry spaghetti noodles {a} or two cups of uncooked rice {b}?",
        "Would you rather eat a spoonful of wasabi {a} or a spoonful of extremely spicy hot sauce {b}?",
        "Would you rather have hands that kept growing as you got older {a} or feet that kept growing as you got older {b}?",
        "Would you rather not be able to open any closed doors (locked {a} or unlocked) {a} or not be able to close any open doors {b}?",
        "Would you rather have plants grow at 20 times their normal rate when you are near {a} or for people and animals to stop aging when you are near them {b}?",
        "Would you rather always feel like someone is following you, but no one is, {a} or always feel like someone is watching you, even though no one is {b}?",
        "Would you rather live in a house with see-through walls in a city {a} or in the same see-through house but in the middle of a forest far from civilization {b}?",
        "Would you rather have every cat {a} or dog that gets lost end up at your house {a} or everyone’s clothes that they forget in the dryer get teleported to your house {b}?",
        "Would you rather blink twice the normal rate {a} or not be able to blink for 5 minutes but then have to close your eyes for 10 seconds every 5 minutes {b}?",
        "Would you rather all plants scream when you cut them / pick their fruit {a} or animals beg for their lives before they are killed {b}?",
        "Would you rather wake up each morning to find that a random animal appendage has replaced your nondominant arm {a} or permanently replace your bottom half with an animal bottom of your choice (not human) {b}?",
        "Would you rather be an amazing virtuoso at any instrument but only if you play naked {a} or be able to speak any language but only if you close your eyes and dance while you are doing it {b}?",
        "Would you rather have a boomerang that would find and kill any one person of your choosing, anywhere in the world, but can only be used once {a} or a boomerang that always returns to you with one dollar {b}?",
        "Would you rather have someone secretly give you LSD on a random day and time once every 6 months {a} or have everyone in the world all take LSD at the same time once every 5 years {b}?",
        "Would you rather all electrical devices mysteriously stop working (possibly forever) {a} or the governments of the world are only run by people going through puberty {b}?",
        "Would you Rather Go Out To Watch A Romantic Movie With Your Crush {a} or Stay Indoors To Watch A Romantic Movie With Your Crush {b}?",
        "Would you Rather Take An Exhausting And Unenjoyable Trip To An Exotic Location For Your Honeymoon, {a} or Have A Relaxing Time At A Dingy Motel Along The Highway {b}?",
        "Would you Rather Go Out For Dinner With Someone You’re Attracted To But Hardly Know {a} or With Someone You Know And Like But Aren’t Attracted To {b}?",
        "Would you Rather Camp Out With Your Partner With Only A Small Survival Kit In A Beautiful Natural Location, {a} or Camp Out In A Comfortable RV In A Trailer Park Without Any Picturesque Views {b}?",
        "Would you Rather Wear A Dirty Outfit {a} or A Torn Outfit {b}?",
        "Would you Rather Have A Partner That Eats A Lot {a} or One That Sleeps A Lot {b}?",
        "Would you Rather Be Kind {a} or Loyal {b}?",
        "If A Relationship Isn’t Working, Would you Rather Be The One To Leave {a} or The One Who Tries To Make It Better {b}?",
        "Would you Rather Be In A Long-Term, Steady, Sometimes Boring Relationship, {a} or An Intense, Steamy Short Term Relationship {b}?",
        "Would you Rather Get 100 Dollars For Kissing A Stranger {a} or A Dollar For Kissing Someone You Love {b}?",
        "Would you Rather Be Able To Remember Everything You Read {a} or Solve Any Math Problem {b}?",
        "Would you Rather Have Super Strength {a} or Super Speed {b}?",
        "Would you Rather Have A Magic Wand {a} or A Cloak That Makes You Invisible {b}?",
        "Would you Rather Build A House {a} or Buy An Existing One {b}?",
        "Would you Rather Forget What You Are Saying While In Charge Of A Huge Presentation {a} or Trip And Fly Across The Floor In Your Office Lobby In Front Of All Your Peers {b}?",
        "Would you Rather Have One Wish Today {a} or Three Wishes In 20 Years {b}?",
        "Would you Rather Wear Clown Shoes Every Day {a} or A Clown Wig Every Day {b}?",
        "Would you Rather Play Video Games {a} or Go On An Outdoor Adventure {b}?",
        "Would you Rather Eat Your Favorite Meal For Every Meal For The Rest Of Your Life {a} or Never Be Able To Eat Your Favorite Meal Again {b}?",
        "Would you Rather Do A Cross Country Trip In A Car {a} or A Train {b}?",
        "Would you Rather Live In Perpetual Summer Heat {a} or Perpetual Winter {b}?",
        "Would you Rather Watch Comedies {a} or Action Movies For The Rest Of Your Life {b}?",
        "Would you Rather Go Camping {a} or Go To Las Vegas {b}?",
        "Would you Rather Be Famous For Athletic Skills {a} or Singing/Acting {b}?",
        "Would you Rather Eat Tacos {a} or Pizza {b}?", "Would you Rather Get Up Early {a} or Stay Up Late {b}?",
        "Would you Rather Be Able To Jump Incredibly High {a} or Run Incredibly Fast {b}?",
        "Would you Rather Never Be Able To Eat Cold Food {a} or Never Be Able To Eat Hot Food {b}?",
        "Would you Rather Hang With A Few Friends {a} or Go To Big Party {b}?",
        "Would you Rather Have Many Good Friends {a} or One Very Best Friend {b}?",
        "Would you Rather Eat Fast Food On All Of Your Dates With A Person You Like {a} or Eat At 5-Star Restaurants With A Person You Dislike {b}?",
        "Would you Rather Have Adventure {a} or Relaxation {b}?" };

    public WouldYouRatherCommand() {
        super(new Types(true, false, false, false));
    }
    
    @Override
    public CommandCategory getCategory() {
        return CommandCategory.UTILITY;
    }
    
    @Override
    public String getDescription() {
        return "Would you rather?";
    }
    
    @Override
    public String getName() {
        return "wouldyourather";
    }

    @Override
    public String getRichName() {
        return "Would You Rather";
    }

    @Override
    public boolean isServerOnly() {
        return true;
    }

    @Override
    public Pair<TimeUnit, Long> getRatelimit() {
        return Pair.of(TimeUnit.SECONDS, 5L);
    }

    @Override
    protected void runSlash(SlashCommandInteractionEvent event) {
        final String question = QUESTIONS[ThreadLocalRandom.current().nextInt(QUESTIONS.length - 1)]
            .replace("{a}", "🅰️").replace("{b}", "🅱️");
        event.deferReply().setContent(question).mentionRepliedUser(false)
            .queue(hook -> hook.retrieveOriginal().queue(msg -> {
                msg.addReaction(Emoji.fromUnicode("U+1F170")).queue();
                msg.addReaction(Emoji.fromUnicode("U+1F171")).queue();
            }));
    }
}
