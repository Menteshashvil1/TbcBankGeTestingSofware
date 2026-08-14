# TBC Bank UI Test Automation

Final Project 1. UI tests for [tbcbank.ge](https://www.tbcbank.ge) written with Java 21, Selenide and TestNG.

| | |
|---|---|
| Site | https://www.tbcbank.ge (English `/en`) |
| Stack | Java 21, Selenide 7.16.2, TestNG 7.11, Maven |
| Profiles | Desktop 1440 x 900, Mobile 390 x 844 |
| Tests | 13 test methods, run on both profiles = 26 runs |
| Result | 25 pass, 1 skipped on purpose (a mobile-only test) |
| Time | about 2 minutes |

```
Tests run: 26, Failures: 0, Errors: 0, Skipped: 1
BUILD SUCCESS
```

## How to run

```bash
mvn test
```

This runs all tests on desktop and mobile at the same time.

Other options:

```bash
mvn test -Dsuite=testng-desktop.xml
```

```bash
mvn test -Dsuite=testng-mobile.xml
```

To watch the tests run in a visible browser:

```bash
mvn test -Dheadless=false
```

You can also use `-Dplatform=mobile` to force one profile.

**About the browser window.** `mvn test` runs headless so 4 windows don't open at once. If you press
Run in IntelliJ you will see real browsers, because IntelliJ runs TestNG directly and does not pass
the `headless` property. Both ways pass.


Screenshots of failures go to `target/screenshots`.

### What you need

JDK 21, Maven 3.9+, and Chrome installed.


## Project structure

```
src/
├── main/java/ge/tbc/testautomation/
│   ├── pages/        only locators
│   ├── steps/        actions and assertions
│   ├── utils/        BrowserConfiguration, Platform, Retry, ScreenshotListener
│   └── constants/    Constants, BrowserConstants, TestData
├── test/java/ge/tbc/testautomation/
│   ├── base/         BaseTest (opens and closes the browser)
│   └── tests/        the 4 test classes
└── testng.xml
```

Pages only hold locators, steps hold the logic. So if a selector changes I only edit one file.

### How desktop and mobile run in parallel

Selenide's `Configuration` is static, so if I set `Configuration.browserSize` the two profiles would
overwrite each other and the mobile tests would secretly run at 1440px. Instead `BrowserConfiguration`
creates the ChromeDriver itself and gives it to Selenide with `WebDriverRunner.setWebDriver()`, which
is per thread. 

I also set the window size with `Emulation.setDeviceMetricsOverride` instead of resizing the
window, because a 1440x900 window only gives a 1424px page (the scrollbar and borders take the rest).
My test `viewportShouldMatchExecutionProfile` found this.

---

## 3.1 Why Automate This?

### Locations - filter by ATM or Branch
`filterByAtmTypeShouldReturnOnlyAtms`, `filterByBranchTypeShouldReturnOnlyBranches`

**Risk.** The filter picks from about 1,100 locations. If it breaks, the page still shows a full list
that looks completely normal, just with the wrong items.It is hard to notice.

**Business value.** Someone filtering for ATMs needs cash now. Sending them to a branch that is closed
means a wasted time.

**Why UI automation.** What I need to check is what the user actually sees after clicking the tab. An
API test could pass while the tab is wired up wrong and shows unfiltered results. The bug would be
between the filter and the list, and only a UI test goes through both.

### Locations - city filter and search
`selectingCityShouldNarrowResults`, `searchingByStreetShouldReturnMatchingLocationsOnly`

**Risk.** The two filters work together (Tbilisi + "Rustaveli" goes 1,132 → 553 → 10). This is usually
where filters break: each one works alone, but the second one resets the first.

**Business value.** Search is how people find one specific branch. If search ignores the city filter,
someone in Tbilisi gets results from Batumi.

**Why UI automation.** The search box is debounced and re-searches while you type. To test it you have
to actually type and wait for the result to settle, which is what a browser test does.

### Locations - map
`mapShouldRenderLocationMarkers`

**Risk.** The map is Google Maps. If the markers stop showing (expired key, quota, SDK change) the
main feature of the page is gone.

**Business value.** The map is how you see which branch is closest. A list alone doesn't answer that.

**Why UI automation.** The markers only exist after Google's script loads in a real browser. There is
no other place to check this.

### Currency calculator
`swappingCurrenciesShouldInvertConversionDirection`, `convertedAmountShouldAgreeWithQuotedRate`

**Risk.** A flipped rate is the bug here. The page would show a normal looking number that is
wrong by about 6.6 times.

**Business value.** This is the number people look at before changing money. For a bank a wrong number
is a trust problem and a complaints problem.

**Why UI automation.** I check that the numbers on the page agree with each other: sell amount, buy
amount and the "1 USD = 2.566 GEL" text. That is a check across several elements on screen. I never
hardcode a rate, I read the rate from the page and check the maths, so the test still works when
rates change during the day.

### Money transfers page and FAQ
`moneyTransfersPageShouldLoadWithBreadcrumbs`, `faqEntryShouldExpandToRevealAnswer`

**Risk.** The FAQ is where people find out how to withdraw a transfer. If the accordion stops opening,
the text is still in the HTML and the page looks fine, but nobody can read it.

**Business value.** Money transfers are a big product for TBC. Every question people can't answer
becomes a phone call.

**Why UI automation.** "The answer can be opened" is about clicking, not about the text existing. Only
a UI test can tell the difference.

### Responsive navigation
`viewportShouldMatchExecutionProfile`, `headerNavigationShouldAdaptToViewport`,
`burgerMenuShouldExposeProductNavigationOnMobile`, `homePageShouldNotOverflowHorizontally`

**Risk.** The header changes completely at the mobile breakpoint. If CSS breaks and the desktop menu
stays at 390px, or the burger stops working, mobile users have no menu at all.

**Business value.** Most banking traffic is mobile. A broken burger means no navigation for most
visitors.

**Why UI automation.** Breakpoints only exist in a real browser at a real size. You can't unit test
this, and it is easy for a manual tester to check on desktop and forget on mobile.

---

## 3.2 Selector Strategy

tbcbank.ge is an Angular site and it has no `data-testid` attributes. The HTML is full of generated
things like `_ngcontent-serverapp-c3286568424`, `ng-star-inserted` and `ng-tns-c3827543319-1` that
change on every build, so I never use those.

**My locator priority (best first):**

1. `id` on form fields - written by developers, unique, doesn't change with styling
2. Angular component tag names - the app's own components
3. Block Element Modifiers and class names 
4. Position inside a component - only when already scoped to a component
5. Visible text - last option, because it changes with language


All selectors are in `Constants.java`.

### 1. `#sell-amount` and `#buy-amount`
*(id)*

The two inputs of the currency calculator. Ids are written by developers and are unique on the page,
so they survive redesigns. **Would break if** the calculator is rebuilt with different ids, or a
second calculator is added to the same page.

### 2. `app-atm-branches-section-list-item`
*(component tag)*

One of these per location in the list. It is the component's own name, so it only changes if someone
renames the component on purpose. It doesn't care about CSS changes at all. **Would break if** the
component is renamed, or the list becomes virtual so only visible rows are in the HTML.

### 3. `.tbcx-pw-atm-branches-section__list-item-icon tbcx-icon`
*(BEM class + component tag)*

The icon on each row. Its text is the icon name (`atm-outlined` or `bank-outlined`).

I picked this after my first idea failed. I first checked the description line, because ATMs show
`ATM - 24/7`. But branches show opening hours like `Week: 10:00-18:00` and never say "Branch". So my
branch test failed. The icon is the only thing that shows the type on every row. **Would break if**
the design system renames the icons or changes them to inline SVG without text. The names are in
`Constants` as `ICON_ATM` and `ICON_BRANCH` so I would only fix one place.

### 4. `tbcx-pw-navigation` and `.tbcx-pw-hamburger-menu__button`
*(component tag + BEM class)*

The desktop menu and the mobile burger. Both are in the HTML on both profiles, so I always check
**visible / not visible**, not "exists". Checking existence would pass on both and prove nothing.
**Would break if** the header is rebuilt, or if they use `*ngIf` instead of CSS to hide them.

### 5. `.tbcx-pw-breadcrumbs__item` found by `href`
*(BEM class + attribute)*

I find breadcrumbs by their link, not their text. On mobile only one breadcrumb is shown and the rest
are hidden, and Selenium's `getText()` returns empty for hidden elements. My first version searched by
text, worked on desktop, and failed on mobile saying "element not found" even though the element was
there. `href` works either way. **Would break if** the URLs change.

---

## 3.3 Flaky Test Awareness

**Test I picked: `searchingByStreetShouldReturnMatchingLocationsOnly`**

It selects Tbilisi, types "Rustaveli", and checks that every result matches. It has every timing
problem this site has.

### 1. Data loads after the page

The page loads first and then gets the 1,132 locations by XHR. For a moment the list exists but is
empty. If I check too early I either fail, or worse, pass on an empty list.

**How I fixed it.** `resultsShouldSettle()` waits until the list has more than 0 items, and I call it
again after every filter change. I wait for the data, not for a number of seconds.

### 2. The debounced search box (this is the one that actually broke)

Typing "Rustaveli" does not go from 553 to 10 in one step. The list re-renders a few times while the
search settles. My first version was:

```java
int sample = Math.min(SAMPLE_SIZE, resultTitles.size());   // reads 15
for (int i = 0; i < sample; i++) { ... }                   // but only 10 are left by then
```

It failed with `IndexOutOfBoundsException: Index 10 out of bounds for length 10`. I read the size
once, then the list got smaller while my loop was still running. This would fail randomly depending
on internet speed.

**How I fixed it.** I stopped looping by index and used one collection check that retries:

```java
locationsPage.resultTitles.shouldHave(
        CollectionCondition.allMatch("title containing '" + term + "'",
                el -> el.getText().toLowerCase().contains(term.toLowerCase())),
        TestData.DATA_REFRESH_TIMEOUT);
```

Selenide checks the whole list again and again until everything matches or the time runs out. There is
no gap between reading the size and using it, because I never read the size.

I fixed `resultTypeShouldBe` the same way, by reading the size again on every loop instead of saving it.



### 3. Popups blocking clicks

The cookie banner covers the filters on a fresh browser, and the locations page asks for your
location, and that Chrome popup blocks clicks.

**How I fixed it.** `dismissCookieBannerIfPresent()` closes the banner and then waits until it is
really gone . For geolocation I set a Chrome
preference (`geolocation: 2`) so the popup never appears. No sleeps.

### 4. Google Maps

The map markers come from Google, so i can not determine loading sped.

**How I fixed it.** A longer timeout 25s and one retry with `@Retry(count = 1)`, only on this test.


### What I did not do

There is no `Thread.sleep` or `Selenide.sleep` anywhere in the project. Every wait is a condition with
a timeout.

---

## 3.4 Mobile is not Desktop

**Scenario: going to a product page from the home page** (`ResponsiveNavigationTests`, plus the
breadcrumb checks in `MoneyTransfersTests`)

### The behaviour is different, not just smaller

On 1440x900 the header shows `tbcx-pw-navigation`, a menu bar with Personal / For Business / TBC that
is always visible and opens on hover. The burger is in the HTML but not visible.

On 390x844 that menu bar is hidden and `.tbcx-pw-hamburger-menu__button` is shown instead. You cannot
reach any product page until you open and se full panel.

So the two are not the same thing at different sizes. On desktop you look and click. On mobile you
tap, wait for an animation, then look. The menu doesn't exist until you open it.

**Important:** both elements are in the HTML on both profiles. That is why all my checks use
visible / not visible for checking

### Different assertions for each profile

```java
if (Platform.isMobile()) {
    burgerButton.shouldBe(visible);
    desktopNavigation.shouldNotBe(visible);
} else {
    desktopNavigation.shouldBe(visible);
    desktopNavigationItems.first().shouldBe(visible);
    burgerButton.shouldNotBe(visible);
}
```

Each profile checks what should be there **and** what should not. If both showed at once, a one-sided
check would still pass, even though a 266px menu bar in a 390px header is broken.

`burgerMenuShouldExposeProductNavigationOnMobile` has no desktop version at all and thats why 1 test is always skipped


- **Desktop** shows the whole path: Home > Other Products > Money Transfers
- **Mobile** shows only one, the parent page, as a back link

So I check how many are **visible**:

```java
if (Platform.isMobile()) {
    visibleBreadcrumbs().shouldHave(size(1));
    breadcrumbFor(parentHref).shouldBe(visible);
    breadcrumbFor(currentPageHref).shouldNotBe(visible);
} else {
    breadcrumbFor(currentPageHref).shouldBe(visible);
    visibleBreadcrumbs().shouldHave(sizeGreaterThan(1));
}
```

Mobile gives you "go back one level", desktop gives you "jump anywhere".

The locations list is also different: desktop is a vertical list you scroll, mobile is a card carousel
you swipe sideways over the map. The rows are outside the screen on purpose, which is why my type
filter test reads the icon text instead of requiring the row to be visible.

That difference turned out to be my second bug. With 1,132 results the mobile carousel is 370,182 px
wide and shows 2 cards at a time, so reaching the end would take about 948 swipes, and the page never
says how many results there were. Desktop has no horizontal scrolling at all. I only found this
because I was measuring both profiles for the tests. Reported as **BUG-02**.

### UX risks I found

1. **On mobile everything depends on the burger.** On desktop, if the menu breaks, the top links are
   still normal links. On mobile a broken burger means no navigation at all. Same bug, but bigger issue
2. **Every mobile journey needs one more tap and an animation.** If the panel is slow it affects the
   whole visit.
3. **Losing the breadcrumbs means losing your place.** On mobile you only see one step back and you
   don't know where you are in the site.
4. **Content off screen looks like it's missing.** The carousel shows 2 cards at a time with no total
   count, so you can't tell if there are 3 results or 1,132. Even after filtering to one city there
   are still 553 results in a strip 180,849 px wide. This is **BUG-02**.

---

## Deliverables

| Item | Where |
|---|---|
| Automated tests (13 methods, 2 profiles) | `src/test/java/ge/tbc/testautomation/tests/` |
| Parallel test suite | `testng.xml` |
| Bug reports (2) | [`docs/BUG-REPORTS.md`](docs/BUG-REPORTS.md) |
| Screenshots for the bugs | `docs/evidence/` |

### about the loan calculator

The task PDF suggests a loan calculator scenario as an example. I did not automate it because
tbcbank.ge does not have a loan calculator. The home page has a card saying "Calculate the loan - Use
the calculator and pre-calculate the loan terms", but it links to `/en/loans`, which has no inputs and
no calculator. I reported this as **BUG-01** instead.
