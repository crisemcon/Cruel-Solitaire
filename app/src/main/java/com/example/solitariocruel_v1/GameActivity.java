package com.example.solitariocruel_v1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class GameActivity extends AppCompatActivity implements Serializable {

    public class Stack extends java.util.Stack<Card> {}
    public class Deck extends java.util.Vector<Card> {}

    DBHelper db;
    Deck [] decks;
    Stack [] stacks;
    Deck shuffler;
    String jugador;
    boolean music, timerShow;

    Chronometer timer;

    MediaPlayer backMusic;

    private ImageButton[] decksbtns;
    private ImageButton[] stacksbtns;
    private ImageButton redealer;
    private TextView name;
    private TextView timeText;
    private TextView redealstxt;
    private TextView cartasbaseText;

    int redeals, numBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        db = new DBHelper(this);

        decks = new Deck[12];
        stacks = new Stack[4];
        shuffler = new Deck();
        decksbtns = new ImageButton[12];
        stacksbtns = new ImageButton[4];
        redeals = 0;
        numBase = 4;

        jugador = getIntent().getStringExtra("nombre");
        timerShow = getIntent().getBooleanExtra("timerOp",true);
        music = getIntent().getBooleanExtra("musicaOp",true);

        findViews();

        name.setText(jugador);
        if(!timerShow) {
            timer.setVisibility(View.GONE);
            timeText.setVisibility(View.GONE);
        }

        backMusic = MediaPlayer.create(this, R.raw.jazzmusic);
        backMusic.setLooping(true);
        if(music) {
            backMusic.start();
        }

        cartasbaseText.setText(Integer.toString(numBase));
        redealstxt.setText(Integer.toString(redeals));

        startGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (backMusic != null && music){
            backMusic.pause();
            if (isFinishing()){
                backMusic.pause();
            }
        }
    }

    @Override
    protected void onResume() {
        if(music && backMusic != null && !backMusic.isPlaying()) {
            backMusic.start();
        }
        super.onResume();
    }

    public void startGame(){
        startDeck();
        shuffleCards();
        dealCards();
        paint();
        timer.start();
    }

    public void startDeck(){
        for( int i = 2; i <= 13; i++ ) {
            shuffler.add( new Card( Card.CardType.CORAZON, i ) );
            shuffler.add( new Card( Card.CardType.DIAMANTE, i ) );
            shuffler.add( new Card( Card.CardType.PICA, i ) );
            shuffler.add( new Card( Card.CardType.TREBOL, i ) );
        }
        stacks[0] = new Stack();
        stacks[1] = new Stack();
        stacks[2] = new Stack();
        stacks[3] = new Stack();

        stacks[0].push( new Card( Card.CardType.CORAZON, 1));
        stacks[1].push( new Card( Card.CardType.DIAMANTE, 1));
        stacks[2].push( new Card( Card.CardType.PICA, 1));
        stacks[3].push( new Card( Card.CardType.TREBOL, 1));
    }

    public void findViews(){
        decksbtns[0] = findViewById(R.id.deck1);
        decksbtns[1] = findViewById(R.id.deck2);
        decksbtns[2] = findViewById(R.id.deck3);
        decksbtns[3] = findViewById(R.id.deck4);
        decksbtns[4] = findViewById(R.id.deck5);
        decksbtns[5] = findViewById(R.id.deck6);
        decksbtns[6] = findViewById(R.id.deck7);
        decksbtns[7] = findViewById(R.id.deck8);
        decksbtns[8] = findViewById(R.id.deck9);
        decksbtns[9] = findViewById(R.id.deck10);
        decksbtns[10] = findViewById(R.id.deck11);
        decksbtns[11] = findViewById(R.id.deck12);

        stacksbtns[0] = findViewById(R.id.stack1);
        stacksbtns[1] = findViewById(R.id.stack2);
        stacksbtns[2] = findViewById(R.id.stack3);
        stacksbtns[3] = findViewById(R.id.stack4);

        redealer = findViewById(R.id.redeal);
        redealstxt = findViewById(R.id.redeals);
        cartasbaseText = findViewById(R.id.numcartas);

        timer = (Chronometer) findViewById(R.id.timer);

        name = (TextView) findViewById(R.id.nickname);
        timeText = (TextView) findViewById(R.id.timeText);
    }

    public void shuffleCards(){
        for( int round=0; round<200; round++ ) {
            int position = (int) ( Math.random() * shuffler.size() );
            Card removedCard = shuffler.elementAt( position );
            shuffler.removeElementAt( position );
            shuffler.add( removedCard );
        }
    }

    public void dealCards(){
        Card removed;
        for (int i = 0; i < 12; i++){
            decks[i] = new Deck();
            for (int j = 0; j < 4; j++){
                //int position = (int) ( Math.random() * shuffler.size() );
                int position = (int) ( Math.random() % shuffler.size() );
                removed = shuffler.elementAt(position);
                shuffler.removeElementAt(position);
                decks[i].add(removed);
            }
        }
        /*for (int i = 0; i < 12; i++) {
            decks[i] = new Deck();
            for (int j = 0; j < 4; j++) {
                decks[i].add(shuffler.lastElement());
                shuffler.removeElementAt(shuffler.size() - 1);

            }
        }*/
    }

    public void paint(){
        //Toast.makeText(getApplicationContext(), decks[0].lastElement().toString(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 12; i++){
            if (decks[i].size() == 0){
                decksbtns[i].setImageResource(R.color.negroGame);
                continue;
            }

            if (decks[i].lastElement().toString().equals("CORAZON 2")){
                decksbtns[i].setImageResource(R.drawable.cardhearts2);
            } else if(decks[i].lastElement().toString().equals("CORAZON 3")){
                decksbtns[i].setImageResource(R.drawable.cardhearts3);
            } else if(decks[i].lastElement().toString().equals("CORAZON 4")){
                decksbtns[i].setImageResource(R.drawable.cardhearts4);
            } else if(decks[i].lastElement().toString().equals("CORAZON 5")){
                decksbtns[i].setImageResource(R.drawable.cardhearts5);
            } else if(decks[i].lastElement().toString().equals("CORAZON 6")){
                decksbtns[i].setImageResource(R.drawable.cardhearts6);
            } else if(decks[i].lastElement().toString().equals("CORAZON 7")){
                decksbtns[i].setImageResource(R.drawable.cardhearts7);
            } else if(decks[i].lastElement().toString().equals("CORAZON 8")){
                decksbtns[i].setImageResource(R.drawable.cardhearts8);
            } else if(decks[i].lastElement().toString().equals("CORAZON 9")){
                decksbtns[i].setImageResource(R.drawable.cardhearts9);
            } else if(decks[i].lastElement().toString().equals("CORAZON 10")){
                decksbtns[i].setImageResource(R.drawable.cardhearts10);
            } else if(decks[i].lastElement().toString().equals("CORAZON 11")){
                decksbtns[i].setImageResource(R.drawable.cardheartsj);
            } else if(decks[i].lastElement().toString().equals("CORAZON 12")){
                decksbtns[i].setImageResource(R.drawable.cardheartsq);
            } else if(decks[i].lastElement().toString().equals("CORAZON 13")){
                decksbtns[i].setImageResource(R.drawable.cardheartsk);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 2")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds2);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 3")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds3);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 4")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds4);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 5")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds5);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 6")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds6);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 7")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds7);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 8")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds8);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 9")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds9);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 10")){
                decksbtns[i].setImageResource(R.drawable.carddiamonds10);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 11")){
                decksbtns[i].setImageResource(R.drawable.carddiamondsj);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 12")){
                decksbtns[i].setImageResource(R.drawable.carddiamondsq);
            } else if(decks[i].lastElement().toString().equals("DIAMANTE 13")){
                decksbtns[i].setImageResource(R.drawable.carddiamondsk);
            } else if(decks[i].lastElement().toString().equals("PICA 2")){
                decksbtns[i].setImageResource(R.drawable.cardspades2);
            } else if(decks[i].lastElement().toString().equals("PICA 3")){
                decksbtns[i].setImageResource(R.drawable.cardspades3);
            } else if(decks[i].lastElement().toString().equals("PICA 4")){
                decksbtns[i].setImageResource(R.drawable.cardspades4);
            } else if(decks[i].lastElement().toString().equals("PICA 5")){
                decksbtns[i].setImageResource(R.drawable.cardspades5);
            } else if(decks[i].lastElement().toString().equals("PICA 6")){
                decksbtns[i].setImageResource(R.drawable.cardspades6);
            } else if(decks[i].lastElement().toString().equals("PICA 7")){
                decksbtns[i].setImageResource(R.drawable.cardspades7);
            } else if(decks[i].lastElement().toString().equals("PICA 8")){
                decksbtns[i].setImageResource(R.drawable.cardspades8);
            } else if(decks[i].lastElement().toString().equals("PICA 9")){
                decksbtns[i].setImageResource(R.drawable.cardspades9);
            } else if(decks[i].lastElement().toString().equals("PICA 10")){
                decksbtns[i].setImageResource(R.drawable.cardspades10);
            } else if(decks[i].lastElement().toString().equals("PICA 11")){
                decksbtns[i].setImageResource(R.drawable.cardspadesj);
            } else if(decks[i].lastElement().toString().equals("PICA 12")){
                decksbtns[i].setImageResource(R.drawable.cardspadesq);
            } else if(decks[i].lastElement().toString().equals("PICA 13")){
                decksbtns[i].setImageResource(R.drawable.cardspadesk);
            } else if(decks[i].lastElement().toString().equals("TREBOL 2")){
                decksbtns[i].setImageResource(R.drawable.cardclubs2);
            } else if(decks[i].lastElement().toString().equals("TREBOL 3")){
                decksbtns[i].setImageResource(R.drawable.cardclubs3);
            } else if(decks[i].lastElement().toString().equals("TREBOL 4")){
                decksbtns[i].setImageResource(R.drawable.cardclubs4);
            } else if(decks[i].lastElement().toString().equals("TREBOL 5")){
                decksbtns[i].setImageResource(R.drawable.cardclubs5);
            } else if(decks[i].lastElement().toString().equals("TREBOL 6")){
                decksbtns[i].setImageResource(R.drawable.cardclubs6);
            } else if(decks[i].lastElement().toString().equals("TREBOL 7")){
                decksbtns[i].setImageResource(R.drawable.cardclubs7);
            } else if(decks[i].lastElement().toString().equals("TREBOL 8")){
                decksbtns[i].setImageResource(R.drawable.cardclubs8);
            } else if(decks[i].lastElement().toString().equals("TREBOL 9")){
                decksbtns[i].setImageResource(R.drawable.cardclubs9);
            } else if(decks[i].lastElement().toString().equals("TREBOL 10")){
                decksbtns[i].setImageResource(R.drawable.cardclubs10);
            } else if(decks[i].lastElement().toString().equals("TREBOL 11")){
                decksbtns[i].setImageResource(R.drawable.cardclubsj);
            } else if(decks[i].lastElement().toString().equals("TREBOL 12")){
                decksbtns[i].setImageResource(R.drawable.cardclubsq);
            } else if(decks[i].lastElement().toString().equals("TREBOL 13")) {
                decksbtns[i].setImageResource(R.drawable.cardclubsk);
            }
        }
        for (int i = 0; i < 4; i++){
            if (i == 0){
                if (stacks[i].lastElement().toString().equals("CORAZON 2")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts2);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 3")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts3);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 4")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts4);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 5")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts5);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 6")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts6);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 7")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts7);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 8")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts8);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 9")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts9);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 10")){
                    stacksbtns[i].setImageResource(R.drawable.cardhearts10);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 11")){
                    stacksbtns[i].setImageResource(R.drawable.cardheartsj);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 12")){
                    stacksbtns[i].setImageResource(R.drawable.cardheartsq);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 13")){
                    stacksbtns[i].setImageResource(R.drawable.cardheartsk);
                } else if(stacks[i].lastElement().toString().equals("CORAZON 1")){
                    stacksbtns[i].setImageResource(R.drawable.cardheartsa);
                }
            }
            else if (i == 1){
                if(stacks[i].lastElement().toString().equals("DIAMANTE 2")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds2);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 3")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds3);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 4")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds4);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 5")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds5);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 6")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds6);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 7")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds7);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 8")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds8);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 9")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds9);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 10")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamonds10);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 11")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamondsj);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 12")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamondsq);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 13")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamondsk);
                } else if(stacks[i].lastElement().toString().equals("DIAMANTE 1")){
                    stacksbtns[i].setImageResource(R.drawable.carddiamondsa);
                }
            }
            else if (i == 2){
                if(stacks[i].lastElement().toString().equals("PICA 2")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades2);
                } else if(stacks[i].lastElement().toString().equals("PICA 3")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades3);
                } else if(stacks[i].lastElement().toString().equals("PICA 4")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades4);
                } else if(stacks[i].lastElement().toString().equals("PICA 5")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades5);
                } else if(stacks[i].lastElement().toString().equals("PICA 6")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades6);
                } else if(stacks[i].lastElement().toString().equals("PICA 7")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades7);
                } else if(stacks[i].lastElement().toString().equals("PICA 8")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades8);
                } else if(stacks[i].lastElement().toString().equals("PICA 9")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades9);
                } else if(stacks[i].lastElement().toString().equals("PICA 10")){
                    stacksbtns[i].setImageResource(R.drawable.cardspades10);
                } else if(stacks[i].lastElement().toString().equals("PICA 11")){
                    stacksbtns[i].setImageResource(R.drawable.cardspadesj);
                } else if(stacks[i].lastElement().toString().equals("PICA 12")){
                    stacksbtns[i].setImageResource(R.drawable.cardspadesq);
                } else if(stacks[i].lastElement().toString().equals("PICA 13")){
                    stacksbtns[i].setImageResource(R.drawable.cardspadesk);
                } else if(stacks[i].lastElement().toString().equals("PICA 1")){
                    stacksbtns[i].setImageResource(R.drawable.cardspadesa);
                }
            }
            else if (i == 3){
                if(stacks[i].lastElement().toString().equals("TREBOL 2")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs2);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 3")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs3);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 4")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs4);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 5")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs5);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 6")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs6);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 7")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs7);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 8")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs8);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 9")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs9);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 10")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubs10);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 11")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubsj);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 12")){
                    stacksbtns[i].setImageResource(R.drawable.cardclubsq);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 13")) {
                    stacksbtns[i].setImageResource(R.drawable.cardclubsk);
                } else if(stacks[i].lastElement().toString().equals("TREBOL 1")) {
                    stacksbtns[i].setImageResource(R.drawable.cardclubsa);
                }
            }
        }

        for (int i=0; i<12; i++){
            for (int j=0; j<10; j++){
                if (decks[i].size() > j) {
                    Log.d("debug", Integer.toString(i) + "   " + decks[i].elementAt(j).toString());
                }
                else break;
            }
        }


        if (isFinish()) {
            timer.stop();
            if(music) {
                backMusic.stop();
                //backMusic.release();
            }
            //Toast.makeText(getApplicationContext(), "GANASTE!", Toast.LENGTH_SHORT).show();
            db.openDB();
            db.addJugador(jugador, numBase, (String)timer.getText(), redeals);
            db.closeDB();

            MediaPlayer mp = MediaPlayer.create(this, R.raw.win);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });


            Intent i = new Intent(this, EndActivity.class);
            String mensaje1 = "Felicidades,";
            String mensaje2 = "Has ganado el juego!";
            i.putExtra("mensaje1",mensaje1);
            i.putExtra("nombre", jugador);
            i.putExtra("mensaje2", mensaje2);
            startActivity(i);
            finish();
        }
    }

    public int canMoveToStack(Card card){
        for( int stackIndex=0; stackIndex < 4; stackIndex++ ) {
            Stack stack = this.stacks[stackIndex];
            if ( ! stack.isEmpty() ) {
                if ( stack.lastElement().getType() != card.getType() ) continue;
                if ( stack.lastElement().getValue() == card.getValue()-1 ) {
                    //cartasbaseText.setText(Integer.toString(numBase));
                    return stackIndex;
                }
            }
        }
        return -1;
    }

    public int canMoveCardToDeck( Card card ) {
        for( int deckIndex = 0; deckIndex < 12; deckIndex++ ) {
            Deck deck = this.decks[deckIndex];
            if ( deck.size() > 0 ) {
                if ( deck.lastElement().getType() != card.getType() ) continue;
                if ( deck.lastElement().getValue() == card.getValue()+1 ) return deckIndex;
            }
        }
        return -1;
    }

    public boolean isFinish() {
        return stacks[0].isEmpty() == false && stacks[0].lastElement().getValue() == 13 &&
                stacks[1].isEmpty() == false && stacks[1].lastElement().getValue() == 13 &&
                stacks[2].isEmpty() == false && stacks[2].lastElement().getValue() == 13 &&
                stacks[3].isEmpty() == false && stacks[3].lastElement().getValue() == 13;
    }

    public void onredeal(View v){
        int last = getLast();
        boolean changed = false;
        for (int k = 0; k < 11; k++) {
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 4; j++) {
                    if (decks[i].size() == 4) break;
                    if (decks[i].size() < 4 && decks[i + 1].size() != 0) {
                        changed = true;
                        decks[i].add(0, decks[i + 1].lastElement());
                        decks[i + 1].removeElementAt(decks[i + 1].size() - 1);
                    } else if (decks[i].size() > 4) {
                        changed = true;
                        decks[i + 1].add(decks[i].firstElement());
                        decks[i].removeElementAt(0);
                    }
                }
            }
        }
        if (getLast() == last && !anymoveleft()) {
            timer.stop();
            if(music) {
                backMusic.stop();
                //backMusic.release();
            }
            //Toast.makeText(getApplicationContext(), "NO HAY MÁS JUGADAS DISPONIBLES!", Toast.LENGTH_SHORT).show();
            db.openDB();
            db.addJugador(jugador, numBase, (String)timer.getText(), redeals);
            db.closeDB();

            MediaPlayer mp = MediaPlayer.create(this, R.raw.lose);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            Intent i = new Intent(this, EndActivity.class);
            String mensaje1 = "Mejor suerte a la próxima,";
            String mensaje2 = "Te quedaste sin movimientos \n Has perdido el juego!";
            i.putExtra("mensaje1",mensaje1);
            i.putExtra("nombre", jugador);
            i.putExtra("mensaje2", mensaje2);
            startActivity(i);
            finish();
        }
        else {
            if (changed == true) {
                MediaPlayer mp = MediaPlayer.create(this, R.raw.dealcards);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        mp.release();
                    }
                });
                redeals++;
            }
            redealstxt.setText(Integer.toString(redeals));
            paint();
        }
    }

    public boolean anymoveleft(){
        for(int i=0; i<12; i++){
            if (decks[i].isEmpty()) continue;
            if (canMoveCardToDeck(decks[i].lastElement()) != -1) return true;
            if (canMoveToStack(decks[i].lastElement()) != -1) return true;
        }
        return false;
    }

    public int getLast() {
        for(int i=11;i>=0;i--){
            if(!decks[i].isEmpty()) {
                return decks[i].size();
            }
        }
        return -1;
    }

    public void ondeck1(View v){
        if (decks[0].size() == 0) return;

        Card aux = decks[0].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[0].removeElementAt(decks[0].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[0].removeElementAt(decks[0].size()-1);
            paint();
        }
    }
    public void ondeck2(View v){
        if (decks[1].size() == 0) return;

        Card aux = decks[1].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[1].removeElementAt(decks[1].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[1].removeElementAt(decks[1].size()-1);
            paint();
        }
    }
    public void ondeck3(View v){
        if (decks[2].size() == 0) return;

        Card aux = decks[2].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[2].removeElementAt(decks[2].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[2].removeElementAt(decks[2].size()-1);
            paint();
        }
    }
    public void ondeck4(View v){
        if (decks[3].size() == 0) return;

        Card aux = decks[3].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[3].removeElementAt(decks[3].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[3].removeElementAt(decks[3].size()-1);
            paint();
        }
    }
    public void ondeck5(View v){
        if (decks[4].size() == 0) return;

        Card aux = decks[4].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[4].removeElementAt(decks[4].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[4].removeElementAt(decks[4].size()-1);
            paint();
        }
    }
    public void ondeck6(View v){
        if (decks[5].size() == 0) return;

        Card aux = decks[5].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[5].removeElementAt(decks[5].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[5].removeElementAt(decks[5].size()-1);
            paint();
        }
    }
    public void ondeck7(View v){
        if (decks[6].size() == 0) return;

        Card aux = decks[6].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[6].removeElementAt(decks[6].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[6].removeElementAt(decks[6].size()-1);
            paint();
        }
    }
    public void ondeck8(View v){
        if (decks[7].size() == 0) return;

        Card aux = decks[7].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[7].removeElementAt(decks[7].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[7].removeElementAt(decks[7].size()-1);
            paint();
        }
    }
    public void ondeck9(View v){
        if (decks[8].size() == 0) return;

        Card aux = decks[8].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[8].removeElementAt(decks[8].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[8].removeElementAt(decks[8].size()-1);
            paint();
        }
    }
    public void ondeck10(View v){
        if (decks[9].size() == 0) return;

        Card aux = decks[9].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[9].removeElementAt(decks[9].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[9].removeElementAt(decks[9].size()-1);
            paint();
        }
    }
    public void ondeck11(View v){
        if (decks[10].size() == 0) return;

        Card aux = decks[10].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[10].removeElementAt(decks[10].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[10].removeElementAt(decks[10].size()-1);
            paint();
        }
    }
    public void ondeck12(View v){
        if (decks[11].size() == 0) return;

        Card aux = decks[11].lastElement();
        int stackind = canMoveToStack(aux);
        int deckind = canMoveCardToDeck(aux);
        if (stackind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            numBase++;
            cartasbaseText.setText(Integer.toString(numBase));
            stacks[stackind].add(aux);
            decks[11].removeElementAt(decks[11].size()-1);
            paint();
        }
        else if (deckind != -1){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.cardflip);
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });

            decks[deckind].add(aux);
            decks[11].removeElementAt(decks[11].size()-1);
            paint();
        }
    }
}
