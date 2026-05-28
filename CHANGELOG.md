# Change Log

All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]

## [0.3.0 - 2026-05-28]
- `pj/lay-*` with a non-matching position on a leaf-with-position now promotes the leaf into a 2-panel composite (the layer goes on a new sub-pose), mirroring how the same call already behaves on a composite (Rule LP3). Previously this threw "conflict with the pose's existing position". The Identity rule applies symmetrically: non-matching columns create a new leaf, whether the receiver is a leaf or a composite.
- `pj/lay-*` now raises a focused "column doesn't exist in the data the new sub-pose would use" error when the new position columns (under LP2 promotion or LP3 composite append) are absent from the inherited data and the layer carries no own `:data`. Catches typos at the call site rather than deferring to a generic plan-stage "column not found".
- When a layer carries its own `:data` and the pose's position columns are absent from that data, the column-validation error now names the source ("inherited from the pose's mapping") and offers two paths: rename the column to align with the pose's position for an overlay, or set the axis on the layer call to create a separate sub-pose. Previously the user saw a generic "Column ... not found" with no hint about why the panel's column was being looked up in the layer's own data.

## [0.2.2 - 2026-05-19]
- fix: `pj/scale :y :log` now works on histograms and categorical bar charts. (Closes #5) - thanks, @harold.
- fix: SVG coordinate formatter now pins `java.util.Locale/ROOT`, so plots render correctly on JVMs whose default locale uses comma as the decimal separator (Czech, German, etc.). (PR #3) - thanks, @tombarys

## [0.2.1 - 2026-05-09]
- `pj/scale` accepts `:labels` paired with `:breaks` -- render numeric tick positions with custom text (e.g. days of the week 1-7 labelled "Mon"-"Sun" on a tile heatmap). Length must match `:breaks`; `:labels` without `:breaks` throws.
- docstring updates

## [0.2.0 - 2026-05-05]
- the membrane stage now returns a `PlotjeMembrane` record implementing the [Membrane](https://github.com/phronmophobic/membrane) UI protocols (`IOrigin`, `IBounds`, `IChildren`), so Plotje plots compose with hand-built Membrane elements. Width and height read via `(membrane.ui/width m)`/`(height m)`; title rides as `:plotje/title`. Replaces the prior metadata-tagged-vector contract.
- new `pj/membrane?` predicate

## [0.1.0 - 2026-05-03]
- initial public alpha release
- composable five-stage pipeline: pose -> draft -> plan -> membrane -> plot
- layer types for distributions, ranking, time series, relationships, and polar
- composite poses with faceting and shared scales
- SVG and PNG rendering via membrane
