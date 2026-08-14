# Bug Reports — tbcbank.ge

Two independent, reproducible defects found during testing. They affect different pages, different
components and different failure modes — neither is a variant of the other.

**BUG-02 was found at the required mobile resolution of 390 x 844.**

| ID | Summary | Type | Severity | Priority | Found at |
|---|---|---|---|---|---|
| [BUG-01](#bug-01) | "Calculate the loan" home-page shortcut leads to a page with no calculator | Content / navigation | Medium | High | 1440 x 900 (also reproduces at 390 x 844) |
| [BUG-02](#bug-02) | On mobile the locations list becomes a 370,000 px wide carousel with no result count | Responsive / usability | Medium | High | **390 x 844** |

---

<a name="bug-01"></a>
## BUG-01 — "Calculate the loan" home-page shortcut leads to a page with no calculator

### Summary

The **Various** section of the home page offers a shortcut card titled **"Calculate the loan"** with
the description *"Use the calculator and pre-calculate the loan terms"*. The card links to
`/en/loans`, which contains no loan calculator of any kind — no amount input, no term slider.
 The user is missled into different page.

### Environment

| Field | Value |
|---|---|
| URL (main page) | https://www.tbcbank.ge/en |
| URL (destination) | https://www.tbcbank.ge/en/loans |
| Browser | Chrome 151.0.7922.109 (also reproduced in Chrome headless ) |
| OS | Windows 11 Pro  |
| Resolution | 1440 x 900 — **also reproduces at 390 x 844** |
| Locale | English (`/en`) — **also reproduces in Georgian (`/ka/loans`)** |


### Preconditions

1. The user is not logged in.
2. Cookie consent has been dismissed.
3. The user is on the English home page, `https://www.tbcbank.ge/en`.

### Steps to reproduce

1. Open `https://www.tbcbank.ge/en`.
2. Scroll down to the section headed **Various**.
3. Locate the middle shortcut card — a calculator icon, the title **"Calculate the loan"** and the
   description **"Use the calculator and pre-calculate the loan terms"**.
4. Click the card.
5. You arrive at `https://www.tbcbank.ge/en/loans`.
6. Scroll through the entire destination page looking for a loan calculator.

### Expected result

Clicking a card that says *"Use the calculator and pre-calculate the loan terms"* lands the user on a
loan calculator: inputs for loan amount and duration, and a calculated monthly payment. Either the
card links to a page that actually contains the calculator, or the card's copy is changed to describe
what `/en/loans` really offers.

### Actual result

`/en/loans` is a product-listing page — Consumer Loan, Mortgage, Installment, Auto Loan, Overdraft,
Online Ganatsileba: each with "Apply" and "Details" links. There is no calculator anywhere on it.

The user must instead guess their way into an individual product page and hunt for a calculator
there.

### Evidence

| File | Shows |
|---|---|
| `evidence/bug1-01-homepage-calculate-the-loan-card.png` | The shortcut card on the home page, outlined in red — title and "Use the calculator…" description is visible |
| `evidence/bug1-02-loans-page-top-no-calculator.png` | Top of `/en/loans` — product cards, no calculator |
| `evidence/bug1-03-loans-page-mid-no-calculator.png` | Middle of `/en/loans` — Auto Loan and "Other loan products", still no calculator |

### Severity and priority

**Severity: Medium.** Nothing errors and no data is at risk, but a  user journey does not
lead where it says it does. The feature the card advertises is simply unreachable from it.

**Priority: High.** It sits in a prominent shortcut block on the home page — one of the most-seen
components on the site — and a loan calculator is high commercial : these are users actively
evaluating borrowing money. The fix is  a link change.



<a name="bug-02"></a>
## BUG-02 — On mobile the locations list becomes a 370,000 px wide carousel you must swipe through one card at a time

### Summary

On desktop the locations page shows the results as a normal vertical list you scroll. At 390 x 844
the same list becomes a horizontal carousel over the map that shows about **2 cards at a time**.
There are 1,132 locations, so the strip is **370,182 px wide** and you would need roughly **948
swipes** to reach the end. There is no result count, no pagination and no "load more", so a mobile
user cannot tell how many results there are or reach anything past the first few.

### Environment

| Field | Value |
|---|---|
| URL | https://www.tbcbank.ge/en/atms&branches |
| Browser | Chrome 151 with device emulation, `deviceMetrics: 390 x 844`, `pixelRatio: 3`, `touch: true` |
| UA | `Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) … Mobile/15E148 Safari/604.1` |
| OS | Windows 11 Pro |
| Resolution | **390 x 844** |
| Locale | English (`/en`) |

### Preconditions

1. The viewport is exactly **390 x 844** with touch enabled (or a real phone of that size).
2. The user is not logged in and the cookie banner has been dismissed.
3. Geolocation is denied, so the page does not re-centre on your own position.

### Steps to reproduce

1. Set the viewport to 390 x 844 and open `https://www.tbcbank.ge/en/atms&branches`.
2. Wait for the locations to load.
3. Look at the results area under the map. Count how many cards you can see.
4. Look anywhere on the page for the number of results found.
5. Measure the carousel in the console:
   ```js
   const list = document.querySelector('.tbcx-pw-atm-branches-section__list');
   const cards = document.querySelectorAll('app-atm-branches-section-list-item');
   ({ results: cards.length, scrollWidth: list.scrollWidth, visibleWidth: list.clientWidth });
   ```
6. Try to reach the last location by swiping.
7. Select a city and measure again.
8. Open the same page at 1440 x 900 and compare.

### Expected result

A mobile user should be able to work through the results, or at least know how many there are. A
vertical scrolling list , or a count plus pagination, or a "show more" button would
all do this. The map is fine as the main view, but the list has to be usable as well.

### Actual result

Measured on the live site:

| | Mobile 390 x 844 | Desktop 1440 x 900 |
|---|---|---|
| Results loaded | 1,132 | 1,132 |
| List direction | horizontal carousel | vertical list |
| Scroll width | **370,182 px** | 470 px (no horizontal scroll) |
| Visible width | 360 px | 470 px |
| Cards visible at once | **2** | 3 (and you can scroll down normally) |
| Distance to the last card | **369,837 px** | 0 |
| Swipes to reach the end | **~948** | 0 |
| Result count shown | **not shown** | not shown |
| Pagination or "load more" | **none** | none |


**Filtering does not fix it.** After selecting Tbilisi the results drop to 553, but the layout is the
same: the lenght is still **180,849 px** wide and still needs about **463 swipes**. You only get a
usable number of results if you also type a street name into the search box, which means you already
know the address you are looking for.

### Evidence

| File | Shows |
|---|---|
| `evidence/bug2-01-mobile-results-carousel.png` | The page at 390 x 844. Only one card ("#16, Kazbegi Ave.") is visible under the map, and the floating chat button covers part of it. |
| `evidence/bug2-02-desktop-results-list.png` | The same page at 1440 x 900, where the results are a normal vertical list next to the map. |

### Severity and priority

**Severity: Medium.** The page still works and the map is usable, but the results list is not really
usable on a phone. The user cannot see how many locations were found and cannot reach most of them.

**Priority: High.** This is the main self-service page on the site and most banking traffic is mobile.
Someone standing in the street looking for the nearest ATM is exactly the person who gets the worst
version of this page.

