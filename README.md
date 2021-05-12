# IKT205-Prosjekt-2

Prosjekt 2 i IKT205 ved UiA.

En tre på rad app hvor du kan spille mot en motstander via en web-tjeneste.

I hoved menyen kan man velge å bli med i ett spill eller lage ett nytt spill.
[![Create Game](https://raw.githubusercontent.com/W-Johansen/IKT205-Prosjekt-2/master/img/main-menu.png "Create Game")](https://raw.githubusercontent.com/W-Johansen/IKT205-Prosjekt-2/master/img/main-menu.png "Create Game")

Om man velger nytt spill starter `GameActivity` og man blir gitt en GameID som man gir til motstanderen så de kan koble til spillet. Så må en vente på at motstanderen trykker *JOIN GAME* og skriver inn GameIDen.

Når en motstander er tilkoblet starter spillet, det er alltid hosten som tar første trekk.
[![Waiting for opponent](https://raw.githubusercontent.com/W-Johansen/IKT205-Prosjekt-2/master/img/create-game.png "Waiting for opponent")](https://raw.githubusercontent.com/W-Johansen/IKT205-Prosjekt-2/master/img/create-game.png "Waiting for opponent")

Når en har vunnet, eller det er uavgjort, kan begge spillerene trykke på *PLAY AGAIN* for å ta ett nytt spill. 
[![You win!](https://raw.githubusercontent.com/W-Johansen/IKT205-Prosjekt-2/master/img/you-win.png "You win!")](https://raw.githubusercontent.com/W-Johansen/IKT205-Prosjekt-2/master/img/you-win.png "You win!")
